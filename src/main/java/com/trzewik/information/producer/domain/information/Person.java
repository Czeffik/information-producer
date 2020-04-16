package com.trzewik.information.producer.domain.information;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Person {
    @NonNull
    private final String id;
    @NonNull
    private final String name;
    @NonNull
    private final String lastName;

    Person(@NonNull InformationService.PersonForm form) {
        this(
            UUID.randomUUID().toString(),
            form.getName(),
            form.getLastName()
        );
    }

    Person(@NonNull Person person, @NonNull InformationService.PersonForm form) {
        this(
            person.id,
            form.getName() == null ? person.name : form.getName(),
            form.getLastName() == null ? person.lastName : form.getLastName()
        );
    }
}
