package com.trzewik.information.producer.interfaces.rest.information;

import com.trzewik.information.producer.domain.information.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonDto implements Serializable {
    private final String name;
    private final String lastName;

    public static PersonDto from(Person person) {
        return new PersonDto(
            person.getName(),
            person.getLastName()
        );
    }
}
