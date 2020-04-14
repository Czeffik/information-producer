package com.trzewik.information.producer.interfaces.rest;

import com.trzewik.information.producer.domain.information.Information;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InformationDto {
    private final String id;
    private final String description;
    private final String message;
    private final PersonDto person;
    private final List<CarDto> cars;

    public static InformationDto from(Information information) {
        return new InformationDto(
            information.getId(),
            information.getDescription(),
            information.getMessage(),
            PersonDto.from(information.getPerson()),
            information.getCars().stream().map(CarDto::from).collect(Collectors.toList())
        );
    }

}
