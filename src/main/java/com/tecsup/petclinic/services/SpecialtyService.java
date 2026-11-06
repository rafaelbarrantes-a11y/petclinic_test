package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;

public interface SpecialtyService {
    Specialty create(Specialty value);
    Specialty update(Specialty value);
    Specialty findById(Integer id);
    void delete(Integer id);
}
