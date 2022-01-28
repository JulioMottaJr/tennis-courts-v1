package com.tenniscourts.admin;


import com.tenniscourts.config.mapper.MapperConfig;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminDto map(Admin source);

    @InheritInverseConfiguration
    Admin map(AdminDto source);
}

