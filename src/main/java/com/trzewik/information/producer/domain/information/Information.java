package com.trzewik.information.producer.domain.information;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Information {
    private final String id;
    private final String description;
    private final String message;
    private final Person person;
    private final List<Car> cars;
}
