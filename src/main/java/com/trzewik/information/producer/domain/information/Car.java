package com.trzewik.information.producer.domain.information;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Car {
    @NonNull
    private final String id;
    @NonNull
    private final String brand;
    @NonNull
    private final String model;
    @NonNull
    private final String color;

    Car(InformationService.CarForm form) {
        this(
            UUID.randomUUID().toString(),
            form.getBrand(),
            form.getModel(),
            form.getColor()
        );
    }

    Car(Car car, InformationService.CarForm form) {
        this(
            car.id,
            car.brand,
            car.model,
            form.getColor() == null ? car.color : form.getColor()
        );
    }

    boolean isEqual(InformationService.CarForm form) {
        return this.brand.equals(form.getBrand()) && this.model.equals(form.getModel());
    }
}
