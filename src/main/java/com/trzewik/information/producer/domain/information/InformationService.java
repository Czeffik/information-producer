package com.trzewik.information.producer.domain.information;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

public interface InformationService {
    Information get(GetInformationCommand command) throws InformationRepository.NotFoundException;

    Information create(CreateInformationCommand command);

    Information update(UpdateInformationCommand command) throws InformationRepository.NotFoundException;

    Information replace(ReplaceInformationCommand command) throws InformationRepository.NotFoundException;

    Information delete(DeleteInformationCommand command) throws InformationRepository.NotFoundException;

    interface Command {
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    class GetInformationCommand implements Command {
        private final @NonNull String id;
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    class DeleteInformationCommand implements Command {
        private final @NonNull String id;
    }

    @ToString
    class CreateInformationCommand extends InformationCommand {
        public CreateInformationCommand(
            @NonNull String description,
            @NonNull String message,
            @NonNull Person person,
            @NonNull List<Car> cars
        ) {
            super(description, message, person, cars);
        }
    }

    @Getter
    @ToString
    class UpdateInformationCommand extends InformationCommand {
        private final String id;

        public UpdateInformationCommand(
            @NonNull String id,
            String description,
            String message,
            Person person,
            List<Car> cars
        ) {
            super(description, message, person, cars);
            this.id = id;
        }
    }

    @Getter
    @ToString
    class ReplaceInformationCommand extends InformationCommand {
        private final String id;

        public ReplaceInformationCommand(
            @NonNull String id,
            @NonNull String description,
            @NonNull String message,
            @NonNull Person person,
            @NonNull List<Car> cars
        ) {
            super(description, message, person, cars);
            this.id = id;
        }
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    abstract class InformationCommand implements Command {
        private final String description;
        private final String message;
        private final Person person;
        private final List<Car> cars;

        @Getter
        @ToString
        @RequiredArgsConstructor
        public static class Person {
            private final String name;
            private final String lastName;
        }

        @Getter
        @ToString
        @RequiredArgsConstructor
        public static class Car {
            private final String brand;
            private final String model;
            private final String color;
        }
    }
}
