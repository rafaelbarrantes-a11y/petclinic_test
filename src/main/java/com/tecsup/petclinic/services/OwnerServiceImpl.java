package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository repository;

    @Override
    public Owner create(Owner value) {
        if (value.getId() != null) throw new IllegalArgumentException("A new record must not have an id");
        return repository.save(value);
    }

    @Override
    public Owner update(Owner value) {
        findById(value.getId());
        return repository.save(value);
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("An id is required");
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Owner not found: " + id));
    }

    @Override
    public void delete(Integer id) {
        repository.delete(findById(id));
    }
}
