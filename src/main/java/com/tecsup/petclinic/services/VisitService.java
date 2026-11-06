package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;

public interface VisitService {
    Visit create(Visit value);
    Visit update(Visit value);
    Visit findById(Integer id);
    void delete(Integer id);
}
