package com.tenniscourts.schedules;

import com.tenniscourts.config.mapper.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);


    ScheduleDTO map(Schedule source);


    Schedule map(ScheduleDTO source);

    List<ScheduleDTO> map(List<Schedule> source);

}
