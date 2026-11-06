package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.*;
import java.util.List;

public interface VetSpecialtyService {
    VetSpecialty assign(Integer vetId, Integer specialtyId);
    List<Specialty> findSpecialtiesByVet(Integer vetId);
    List<Vet> findVetsBySpecialty(Integer specialtyId);
    void remove(Integer vetId, Integer specialtyId);
}
