package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.*;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class VetSpecialtyServiceImpl implements VetSpecialtyService {
    private final VetSpecialtyRepository repository;
    private final VetService vetService;
    private final SpecialtyService specialtyService;

    @Override
    public VetSpecialty assign(Integer vetId, Integer specialtyId) {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        if (repository.existsById(id)) throw new IllegalArgumentException("Specialty already assigned");
        VetSpecialty value = new VetSpecialty();
        value.setId(id);
        value.setVet(vetService.findById(vetId));
        value.setSpecialty(specialtyService.findById(specialtyId));
        value.setYearsExperience(0);
        value.setIsPrimary(false);
        return repository.save(value);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialty> findSpecialtiesByVet(Integer vetId) {
        return repository.findSpecialtiesByVet(vetId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vet> findVetsBySpecialty(Integer specialtyId) {
        return repository.findVetsBySpecialty(specialtyId);
    }

    @Override
    public void remove(Integer vetId, Integer specialtyId) {
        VetSpecialty value = repository.findById(new VetSpecialtyId(vetId, specialtyId))
                .orElseThrow(() -> new NoSuchElementException("Assignment not found"));
        repository.delete(value);
    }
}
