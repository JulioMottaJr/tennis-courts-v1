package com.tenniscourts.guests;



import com.tenniscourts.config.mapper.MapperConfig;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface GuestMapper {
    GuestMapper INSTANCE = Mappers.getMapper(GuestMapper.class);

    GuestDto map(Guest source);

    @InheritInverseConfiguration
    Guest map(GuestDto source);

    List<GuestDto> map(List<Guest> source);
}

