package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/tennis-court-reservation")
@Api
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;
    @PostMapping
    @ApiOperation("Book a Reservation")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }
    @GetMapping("/{guestId}/{reservationId}")
    @ApiOperation("Find reservation by ID, please insert a guest ID and a reservation ID")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long guestId, @PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(guestId,reservationId));
    }

    @PutMapping("/cancel/{guestId}/{reservationId}")
    @ApiOperation("Cancel a reservation, please insert a guestId and reservation ID")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long guestId, @PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(guestId,reservationId));
    }
    @PutMapping("/reschedule/{guestId}/{reservationId}/{scheduleId}")
    @ApiOperation("Reschedule a reservation, please insert a guest ID, reservationID and scheduleId")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long guestId, @PathVariable Long reservationId,@PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(guestId,reservationId, scheduleId));
    }
}
