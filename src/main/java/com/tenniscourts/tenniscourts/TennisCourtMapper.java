package com.tenniscourts.tenniscourts;


import com.tenniscourts.config.mapper.MapperConfig;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface TennisCourtMapper {
    TennisCourtMapper INSTANCE = Mappers.getMapper(TennisCourtMapper.class);

    TennisCourtDTO map(TennisCourt source);

    @InheritInverseConfiguration
    TennisCourt map(TennisCourtDTO source);
}
