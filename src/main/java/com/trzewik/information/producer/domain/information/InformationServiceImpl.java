package com.trzewik.information.producer.domain.information;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
class InformationServiceImpl implements InformationService {
    private final InformationRepository repository;

    @Override
    public Information get(@NonNull GetInformationCommand getInformationCommand) throws InformationRepository.NotFoundException {
        log.info("Received GetInformationCommand: [{}]", getInformationCommand);
        return repository.get(getInformationCommand.getId());
    }

    @Override
    public Information create(@NonNull CreateInformationCommand createInformationCommand) {
        log.info("Received CreateInformationCommand: [{}]", createInformationCommand);
        Information info = new Information(createInformationCommand);

        repository.save(info);

        return info;
    }

    @Override
    public Information update(@NonNull UpdateInformationCommand updateInformationCommand) throws InformationRepository.NotFoundException {
        log.info("Received UpdateInformationCommand: [{}]", updateInformationCommand);
        Information current = repository.get(updateInformationCommand.getId());
        Information information = new Information(current, updateInformationCommand);
        repository.update(information);
        return information;
    }

    @Override
    public Information replace(@NonNull ReplaceInformationCommand replaceInformationCommand) throws InformationRepository.NotFoundException {
        log.info("Received ReplaceInformationCommand: [{}]", replaceInformationCommand);
        repository.get(replaceInformationCommand.getId());
        Information information = new Information(replaceInformationCommand.getId(), replaceInformationCommand);
        repository.update(information);
        return information;
    }

    @Override
    public Information delete(@NonNull DeleteInformationCommand deleteInformationCommand) throws InformationRepository.NotFoundException {
        log.info("Received DeleteInformationCommand: [{}]", deleteInformationCommand);
        Information information = repository.get(deleteInformationCommand.getId());
        repository.delete(deleteInformationCommand.getId());
        return information;
    }
}
