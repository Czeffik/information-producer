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
    private final String color;

    Car(@NonNull InformationService.CarForm form) {
        this(
            UUID.randomUUID().toString(),
            form.getBrand(),
            form.getModel(),
            form.getColor()
        );
    }

    Car(@NonNull Car car, @NonNull InformationService.CarForm form) {
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
