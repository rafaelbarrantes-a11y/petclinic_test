package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VetSpecialtyRepository extends JpaRepository<VetSpecialty, VetSpecialtyId> {
    @Query("select vs.specialty from VetSpecialty vs where vs.id.vetId = :vetId")
    List<Specialty> findSpecialtiesByVet(@Param("vetId") Integer vetId);

    @Query("select vs.vet from VetSpecialty vs where vs.id.specialtyId = :specialtyId")
    List<Vet> findVetsBySpecialty(@Param("specialtyId") Integer specialtyId);
}
