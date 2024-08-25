package com.rest.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.project.entity.LandmarkType;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLandmarkDto {

    private String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDate;

    private String description;

    private LandmarkType landmarkType;

    private CreateLocalityDto locality;

    private List<CreateServiceDto> services;

}
