package com.trzewik.information.producer.domain.information;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Car {
    @NonNull
    private final String id;
    @NonNull
    private final String brand;
    @NonNull
    private final String model;
    @NonNull
    private final Color color;

    Car(@NonNull InformationService.InformationCommand.Car car) {
        this(
            UUID.randomUUID().toString(),
            car.getBrand(),
            car.getModel(),
            car.getColor()
        );
    }
}
