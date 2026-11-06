package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class VetServiceImpl implements VetService {
    private final VetRepository repository;

    @Override
    public Vet create(Vet value) {
        if (value.getId() != null) throw new IllegalArgumentException("A new record must not have an id");
        return repository.save(value);
    }

    @Override
    public Vet update(Vet value) {
        findById(value.getId());
        return repository.save(value);
    }

    @Override
    @Transactional(readOnly = true)
    public Vet findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("An id is required");
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vet not found: " + id));
    }

    @Override
    public void delete(Integer id) {
        repository.delete(findById(id));
    }
}
