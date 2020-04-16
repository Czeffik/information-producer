package com.trzewik.information.producer.domain.information;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
class InformationServiceImpl implements InformationService {
    private final InformationRepository repository;

    @Override
    public Information get(String id) throws InformationRepository.NotFoundException {
        return repository.get(id);
    }

    @Override
    public Information create(InformationForm form) {
        Information info = new Information(form);

        repository.save(info);

        return info;
    }

    @Override
    public Information update(String id, InformationForm form) throws InformationRepository.NotFoundException {
        Information current = repository.get(id);
        Information information = new Information(current, form);
        repository.update(information);
        return information;
    }

    @Override
    public Information replace(String id, InformationForm form) throws InformationRepository.NotFoundException {
        repository.get(id);
        Information information = new Information(id, form);
        repository.update(information);
        return information;
    }

    @Override
    public Information delete(String id) throws InformationRepository.NotFoundException {
        Information information = repository.get(id);
        repository.delete(id);
        return information;
    }
}
