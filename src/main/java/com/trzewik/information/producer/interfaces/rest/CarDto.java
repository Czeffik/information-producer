package com.trzewik.information.producer.interfaces.rest;

import com.trzewik.information.producer.domain.information.Car;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDto {
    private final String brand;
    private final String model;
    private final String color;

    public static CarDto from(Car car) {
        return new CarDto(
            car.getBrand(),
            car.getModel(),
            car.getColor()
        );
    }
}
