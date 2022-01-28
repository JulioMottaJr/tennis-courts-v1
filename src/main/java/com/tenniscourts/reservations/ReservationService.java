package com.tenniscourts.reservations;

import com.tenniscourts.admin.AdminRepository;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final GuestRepository guestRepository;

    private final ScheduleRepository scheduleRepository;

    private final AdminRepository adminRepository;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        BigDecimal value = new BigDecimal("10.00");
        Reservation reservation = new Reservation();
        Guest guest = guestRepository.findById(createReservationRequestDTO.getGuestId()).orElseThrow(() -> new EntityNotFoundException("GuestId was not found"));
        reservation.setGuest(guest);
        Schedule schedule = scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).orElseThrow(() -> new EntityNotFoundException("ScheduleId was not found"));
        reservation.setSchedule(schedule);
        reservation.setValue(value);
        reservationRepository.save(reservation);
        try {

            return reservationMapper.map(reservation);
        } catch (UnsupportedOperationException e) {

            throw new UnsupportedOperationException(e);
        }
    }

    public ReservationDTO findReservation(Long guestId, Long reservationId) {
        if (!guestRepository.findById(guestId).isPresent()) {
            throw new EntityNotFoundException("GuestId was not found");
        }
        return reservationRepository.findById(reservationId).map(reservationMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long guestId, Long reservationId) {
        return reservationMapper.map(this.cancel(guestId, reservationId));
    }

    private Reservation cancel(Long guestId, Long reservationId) {
        if (!guestRepository.findById(guestId).isPresent()) {
            throw new EntityNotFoundException("GuestId was not found");
        }
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        LocalDateTime actualDate = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(actualDate, reservation.getSchedule().getStartDateTime());
        int hour = actualDate.getHour();
        int minute = actualDate.getMinute();
        if (hours >= 24)
            return reservation.getValue();

        return reservationRefundValidate(reservation, hour, minute);

    }

    public BigDecimal reservationRefundValidate(Reservation reservation, int hour, int minute) {
        if (hour >= 12 && minute >= 00 && hour <= 23 && minute <= 59) {
            return new BigDecimal(25).divide(reservation.getValue());
        } else if (hour >= 2 && minute >= 00 && hour <= 11 && minute <= 59) {
            return new BigDecimal(50).divide(reservation.getValue());
        } else if (hour >= 00 && minute >= 00 && hour <= 1 && minute <= 59) {
            return new BigDecimal(75).divide(reservation.getValue());
        }
        return BigDecimal.ZERO;
    }


    public ReservationDTO rescheduleReservation(Long guestId, Long previousReservationId, Long scheduleId) {
        if (!guestRepository.findById(guestId).isPresent()) {
            throw new EntityNotFoundException("GuestId was not found");
        }
        Reservation previousReservation = cancel(guestId, previousReservationId);

        Schedule schedule = new Schedule();
        schedule.setTennisCourt(previousReservation.getSchedule().getTennisCourt());
        schedule.setEndDateTime(previousReservation.getSchedule().getEndDateTime());
        schedule.setStartDateTime(previousReservation.getSchedule().getStartDateTime());

        previousReservation.setSchedule(schedule);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }
}
