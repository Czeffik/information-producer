package com.trzewik.information.producer.interfaces.rest.information;

import com.trzewik.information.producer.domain.information.Information;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InformationDto implements Serializable {
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
