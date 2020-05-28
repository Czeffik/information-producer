package com.trzewik.information.producer.domain.information;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface InformationService {
    Information get(String id) throws InformationRepository.NotFoundException;

    Information create(InformationForm form);

    Information update(String id, InformationForm form) throws InformationRepository.NotFoundException;

    Information replace(String id, InformationForm form) throws InformationRepository.NotFoundException;

    Information delete(String id) throws InformationRepository.NotFoundException;

    interface Command {
    }

    @Getter
    @RequiredArgsConstructor
    class GetInformationCommand implements Command {
        private final String id;
    }

    @Getter
    @RequiredArgsConstructor
    class DeleteInformationCommand implements Command {
        private final String id;
    }

    class CreateInformationCommand extends InformationCommand {
        public CreateInformationCommand(String description, String message, Person person, List<Car> cars) {
            super(description, message, person, cars);
        }
    }

    @Getter
    class UpdateInformationCommand extends InformationCommand {
        private final String id;

        public UpdateInformationCommand(String id, String description, String message, Person person, List<Car> cars) {
            super(description, message, person, cars);
            this.id = id;
        }
    }

    @Getter
    class ReplaceInformationCommand extends InformationCommand {
        private final String id;

        public ReplaceInformationCommand(String id, String description, String message, Person person, List<Car> cars) {
            super(description, message, person, cars);
            this.id = id;
        }
    }

    @Getter
    @RequiredArgsConstructor
    abstract class InformationCommand implements Command {
        private final String description;
        private final String message;
        private final Person person;
        private final List<Car> cars;

        @Getter
        @RequiredArgsConstructor
        public static class Person {
            private final String name;
            private final String lastName;
        }

        @Getter
        @RequiredArgsConstructor
        public static class Car {
            private final String brand;
            private final String model;
            private final String color;
        }
    }


    @Data
    class InformationForm {
        private String description;
        private String message;
        private PersonForm person;
        private List<CarForm> cars;
    }

    @Data
    class PersonForm {
        private String name;
        private String lastName;
    }

    @Data
    class CarForm {
        private String brand;
        private String model;
        private String color;
    }
}
