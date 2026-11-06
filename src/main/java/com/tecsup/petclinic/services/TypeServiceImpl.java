package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class TypeServiceImpl implements TypeService {
    private final TypeRepository repository;

    @Override
    public Type create(Type value) {
        if (value.getId() != null) throw new IllegalArgumentException("A new record must not have an id");
        return repository.save(value);
    }

    @Override
    public Type update(Type value) {
        findById(value.getId());
        return repository.save(value);
    }

    @Override
    @Transactional(readOnly = true)
    public Type findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("An id is required");
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Type not found: " + id));
    }

    @Override
    public void delete(Integer id) {
        repository.delete(findById(id));
    }
}
