package com.trzewik.information.producer.domain.information;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
class InformationServiceImpl implements InformationService {
    private final InformationReceiver receiver;

    @Override
    public Information getInformation(String id) {
        return receiver.getInformation(id);
    }

}
