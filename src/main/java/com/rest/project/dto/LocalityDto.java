package com.rest.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalityDto {

    private String name;

    private int population;

    private boolean hasMetro;

    private List<LandmarkDto> landmarks;
}
