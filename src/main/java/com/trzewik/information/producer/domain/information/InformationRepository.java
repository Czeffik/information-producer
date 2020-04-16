package com.trzewik.information.producer.domain.information;

import java.util.Optional;

public interface InformationRepository {
    void save(Information information);

    default Information get(String id) throws NotFoundException {
        return find(id).orElseThrow(() -> new NotFoundException(id));
    }

    Optional<Information> find(String id);

    void update(Information information);

    void delete(String id);

    class NotFoundException extends Exception {
        public NotFoundException(String id) {
            super(String.format("Can not find information with id: [%s] in repository.", id));
        }
    }
}
