package com.trzewik.information.producer.domain.information;

import lombok.Data;

import java.util.List;

public interface InformationService {
    Information get(String id) throws InformationRepository.NotFoundException;

    Information create(InformationForm form);

    Information update(String id, InformationForm form) throws InformationRepository.NotFoundException;

    Information replace(String id, InformationForm form) throws InformationRepository.NotFoundException;

    Information delete(String id) throws InformationRepository.NotFoundException;

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
