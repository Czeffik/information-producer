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
public class Person {
    @NonNull
    private final String id;
    @NonNull
    private final String name;
    @NonNull
    private final String lastName;

    Person(@NonNull InformationService.InformationCommand.Person person) {
        this(
            UUID.randomUUID().toString(),
            person.getName(),
            person.getLastName()
        );
    }
}
