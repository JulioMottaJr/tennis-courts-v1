package com.tenniscourts.reservations;

import com.tenniscourts.schedules.Schedule;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10.00)).build()), new BigDecimal(10.00));
    }

    @Test
    public void getRefundWithTwentyFivePercentDiscount(){
        Reservation reservation1 = new Reservation();
        reservation1.setValue(new BigDecimal(10.00));
      BigDecimal refund = reservationService.reservationRefundValidate(reservation1, 12, 30);
       Assert.assertEquals(new BigDecimal(2.5), refund);
    }

    @Test
    public void getRefundWithFiftyPercentDiscount(){
        Reservation reservation1 = new Reservation();
        reservation1.setValue(new BigDecimal(10.00));
        BigDecimal refund = reservationService.reservationRefundValidate(reservation1, 2, 30);
        Assert.assertEquals(new BigDecimal(5.0), refund);
    }

    @Test
    public void getRefundWithSeventyFivePercentDiscount(){
        Reservation reservation1 = new Reservation();
        reservation1.setValue(new BigDecimal(10.00));
        BigDecimal refund = reservationService.reservationRefundValidate(reservation1, 00, 30);
        Assert.assertEquals(new BigDecimal(7.5), refund);
    }
}