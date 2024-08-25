package com.rest.project.mapper;

import com.rest.project.dto.CreateLocalityDto;
import com.rest.project.dto.LocalityDto;
import com.rest.project.entity.Locality;
import net.bytebuddy.asm.Advice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalityMapper {

    Locality toEntity(CreateLocalityDto localityDto);

    CreateLocalityDto toDTO(Locality locality);

    Locality toEntity(LocalityDto localityDto);

}
