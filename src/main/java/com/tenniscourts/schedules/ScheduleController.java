package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tennis-court-schedule")
@Api
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;


    @PostMapping("/addschedule/{adminId}")
    @ApiOperation(value = "Add Schedule to a tennis court, please make sure you write the right format EX: {\n" +
            "  \"endDateTime\": \"2022-01-24T16:00\",\n" +
            "  \"startDateTime\": \"2022-01-24T15:00\",\n" +
            "  \"tennisCourtId\": 2\n" +
            "}")
    public ResponseEntity<Void> addScheduleTennisCourt(@PathVariable Long adminId, @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(adminId,createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @GetMapping("/{startDate}/{endDate}")
    @ApiOperation(value = "Find schedule by dates, please insert a start date and end date, please make sure to use the write format EX: 2022-01-22")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable LocalDate startDate,
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @GetMapping("/schedule/{scheduleId}")
    @ApiOperation(value = "Find schedule by ID, please insert a schedule ID")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
