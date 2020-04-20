package com.trzewik.information.producer.domain.information;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Information {
    @NonNull
    private final String id;
    @NonNull
    private final String description;
    @NonNull
    private final String message;
    @NonNull
    private final Person person;
    @NonNull
    private final List<Car> cars;

    Information(@NonNull InformationService.InformationForm form) {
        this(UUID.randomUUID().toString(), form);
    }

    Information(@NonNull String id, @NonNull InformationService.InformationForm form) {
        this(
            id,
            form.getDescription(),
            form.getMessage(),
            new Person(form.getPerson()),
            form.getCars().stream().map(Car::new).collect(Collectors.toList())
        );
    }

    Information(@NonNull Information information, @NonNull InformationService.InformationForm form) {
        this(
            information.id,
            form.getDescription() == null ? information.description : form.getDescription(),
            form.getMessage() == null ? information.message : form.getMessage(),
            form.getPerson() == null ? information.person : new Person(information.person, form.getPerson()),
            form.getCars() == null ? information.cars : form.getCars().stream()
                .map(carForm -> information.cars.stream()
                    .filter(car -> car.isEqual(carForm))
                    .findFirst()
                    .map(car -> new Car(car, carForm))
                    .orElseGet(() -> new Car(carForm)))
                .collect(Collectors.toList())
        );
    }
}
