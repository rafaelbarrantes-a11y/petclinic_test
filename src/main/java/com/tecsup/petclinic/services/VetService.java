package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;

public interface VetService {
    Vet create(Vet value);
    Vet update(Vet value);
    Vet findById(Integer id);
    void delete(Integer id);
}
