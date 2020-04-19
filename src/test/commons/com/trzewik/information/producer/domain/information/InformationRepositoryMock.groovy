package com.trzewik.information.producer.domain.information

class InformationRepositoryMock implements InformationRepository, InformationCreation {
    private final Map<String, Information> repository = new HashMap<>()

    @Override
    synchronized void save(Information information) {
        repository.put(information.id, createInformation(new InformationCreator(information.id, information)))
    }

    @Override
    synchronized Optional<Information> find(String id) {
        return Optional.ofNullable(repository.get(id))
    }

    @Override
    synchronized void update(Information information) {
        repository.put(information.id, createInformation(new InformationCreator(information.id, information)))
    }

    @Override
    synchronized void delete(String id) {
        repository.remove(id)
    }

    synchronized Map<String, Information> getRepository() {
        return repository
    }
}
