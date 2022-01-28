package com.tenniscourts.schedules;

import com.tenniscourts.admin.AdminRepository;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtRepository tennisCourtRepository;

    private final AdminRepository adminRepository;

    public ScheduleDTO addSchedule(Long adminId, Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        if (!adminRepository.findById(adminId).isPresent()) {
            throw new EntityNotFoundException("Admin ID was not found");
        }
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        schedule.setEndDateTime(createScheduleRequestDTO.getEndDateTime());
        TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId).orElseThrow(() -> new EntityNotFoundException("Tennis court not found"));
        schedule.setTennisCourt(tennisCourt);
         scheduleRepository.save(schedule);


        return scheduleMapper.map(schedule);
    }



    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {


        return scheduleMapper.map(scheduleRepository.findByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(startDate,endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        ScheduleDTO dto = scheduleMapper.map(scheduleRepository.findById(scheduleId).orElseThrow(() -> new EntityNotFoundException("Schedule ID not found")));
        return dto;
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
