package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Type;

public interface TypeService {
    Type create(Type value);
    Type update(Type value);
    Type findById(Integer id);
    void delete(Integer id);
}
