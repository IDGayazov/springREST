package com.rest.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.project.entity.LandmarkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LandmarkDto {
    private String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDate;

    private String description;

    private LandmarkType landmarkType;
}
