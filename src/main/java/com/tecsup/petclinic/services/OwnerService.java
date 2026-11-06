package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;

public interface OwnerService {
    Owner create(Owner value);
    Owner update(Owner value);
    Owner findById(Integer id);
    void delete(Integer id);
}
