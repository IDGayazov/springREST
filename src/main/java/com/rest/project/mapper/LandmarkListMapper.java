package com.rest.project.mapper;

import com.rest.project.dto.CreateLandmarkDto;
import com.rest.project.entity.Landmark;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses=LandmarkMapper.class)
public interface LandmarkListMapper {

    List<CreateLandmarkDto> toDTOList(List<Landmark> landmarks);

}
