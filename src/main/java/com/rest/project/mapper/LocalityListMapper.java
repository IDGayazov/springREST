package com.rest.project.mapper;

import com.rest.project.dto.CreateLocalityDto;
import com.rest.project.entity.Locality;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses=LocalityMapper.class)
public interface LocalityListMapper {
    List<CreateLocalityDto> toDTOList(List<Locality> localityList);
}
