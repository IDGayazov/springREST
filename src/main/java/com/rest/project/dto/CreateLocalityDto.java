package com.rest.project.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocalityDto {

    private String name;

    private int population;

    private boolean hasMetro;
}
