package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository repository;

    @Override
    public Specialty create(Specialty value) {
        if (value.getId() != null) throw new IllegalArgumentException("A new record must not have an id");
        return repository.save(value);
    }

    @Override
    public Specialty update(Specialty value) {
        findById(value.getId());
        return repository.save(value);
    }

    @Override
    @Transactional(readOnly = true)
    public Specialty findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("An id is required");
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Specialty not found: " + id));
    }

    @Override
    public void delete(Integer id) {
        repository.delete(findById(id));
    }
}
