package com.trzewik.information.producer.interfaces.rest.information;

import com.trzewik.information.producer.domain.information.Car;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDto implements Serializable {
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
