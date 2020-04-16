package com.trzewik.information.producer.domain.information;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InformationServiceFactory {

    public static InformationService create(
        InformationRepository informationRepository
    ) {
        return new InformationServiceImpl(informationRepository);
    }
}
