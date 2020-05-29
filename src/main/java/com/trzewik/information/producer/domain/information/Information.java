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

    Information(@NonNull InformationService.InformationCommand command) {
        this(UUID.randomUUID().toString(), command);
    }

    Information(@NonNull String id, @NonNull InformationService.InformationCommand command) {
        this(
            id,
            command.getDescription(),
            command.getMessage(),
            new Person(command.getPerson()),
            command.getCars().stream().map(Car::new).collect(Collectors.toList())
        );
    }

    Information(@NonNull Information information, @NonNull InformationService.InformationCommand command) {
        this(
            information.id,
            command.getDescription() == null ? information.description : command.getDescription(),
            command.getMessage() == null ? information.message : command.getMessage(),
            command.getPerson() == null ? information.person : new Person(command.getPerson()),
            command.getCars() == null ? information.cars : command.getCars().stream().map(Car::new).collect(Collectors.toList())
        );
    }
}
