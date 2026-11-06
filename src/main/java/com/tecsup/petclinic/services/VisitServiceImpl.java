package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitServiceImpl implements VisitService {
    private final VisitRepository repository;

    @Override
    public Visit create(Visit value) {
        if (value.getId() != null) throw new IllegalArgumentException("A new record must not have an id");
        return repository.save(value);
    }

    @Override
    public Visit update(Visit value) {
        findById(value.getId());
        return repository.save(value);
    }

    @Override
    @Transactional(readOnly = true)
    public Visit findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("An id is required");
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Visit not found: " + id));
    }

    @Override
    public void delete(Integer id) {
        repository.delete(findById(id));
    }
}
