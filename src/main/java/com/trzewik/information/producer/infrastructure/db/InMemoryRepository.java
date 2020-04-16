package com.trzewik.information.producer.infrastructure.db;

import com.trzewik.information.producer.domain.information.Information;
import com.trzewik.information.producer.domain.information.InformationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class InMemoryRepository implements InformationRepository {
    private final Map<String, Information> repo;

    @Override
    public synchronized void save(Information information) {
        repo.put(information.getId(), information);
    }

    @Override
    public synchronized Optional<Information> find(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public synchronized void update(Information information) {
        repo.put(information.getId(), information);
    }

    @Override
    public synchronized void delete(String id) {
        repo.remove(id);
    }
}
