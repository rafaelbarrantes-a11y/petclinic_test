package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

class VetSpecialtyServiceTest extends DatabaseServiceTest {
    @Autowired private VetSpecialtyService service;
    private Integer vetId;
    private Integer otherVetId;
    private Integer specialtyId;
    private Integer otherSpecialtyId;

    @BeforeEach
    void fixtures() {
        Vet vet = vet(); em.persist(vet); vetId = vet.getId();
        Vet otherVet = vet(); em.persist(otherVet); otherVetId = otherVet.getId();
        Specialty specialty = specialty(); em.persist(specialty); specialtyId = specialty.getId();
        Specialty otherSpecialty = specialty(); em.persist(otherSpecialty); otherSpecialtyId = otherSpecialty.getId();
        reload();
    }

    @Test
    void testAssignSpecialtyToVet() {
        service.assign(vetId, specialtyId);
        reload();
        VetSpecialty actual = em.find(VetSpecialty.class, new VetSpecialtyId(vetId, specialtyId));
        assertNotNull(actual);
        assertEquals(vetId, actual.getVet().getId());
        assertEquals(specialtyId, actual.getSpecialty().getId());
        assertEquals(0, actual.getYearsExperience());
        assertFalse(actual.getIsPrimary());
        assertEquals(1, countAssignment(vetId, specialtyId));
    }

    @Test
    void testFindSpecialtiesByVet() {
        service.assign(vetId, specialtyId);
        service.assign(vetId, otherSpecialtyId);
        service.assign(otherVetId, specialtyId);
        reload();
        assertEquals(Set.of(specialtyId, otherSpecialtyId), service.findSpecialtiesByVet(vetId)
                .stream().map(Specialty::getId).collect(Collectors.toSet()));
        assertEquals(2, service.findSpecialtiesByVet(vetId).size());
        assertEquals(1, service.findSpecialtiesByVet(otherVetId).size());
    }

    @Test
    void testFindVetsBySpecialty() {
        service.assign(vetId, specialtyId);
        service.assign(otherVetId, specialtyId);
        service.assign(vetId, otherSpecialtyId);
        reload();
        assertEquals(Set.of(vetId, otherVetId), service.findVetsBySpecialty(specialtyId)
                .stream().map(Vet::getId).collect(Collectors.toSet()));
        assertEquals(2, service.findVetsBySpecialty(specialtyId).size());
        assertEquals(1, service.findVetsBySpecialty(otherSpecialtyId).size());
    }

    @Test
    void testRemoveSpecialtyFromVet() {
        service.assign(vetId, specialtyId);
        service.assign(vetId, otherSpecialtyId);
        service.assign(otherVetId, specialtyId);
        reload();
        service.remove(vetId, specialtyId);
        reload();
        assertNull(em.find(VetSpecialty.class, new VetSpecialtyId(vetId, specialtyId)));
        assertEquals(0, countAssignment(vetId, specialtyId));
        assertEquals(Set.of(otherSpecialtyId), service.findSpecialtiesByVet(vetId)
                .stream().map(Specialty::getId).collect(Collectors.toSet()));
        assertEquals(Set.of(otherVetId), service.findVetsBySpecialty(specialtyId)
                .stream().map(Vet::getId).collect(Collectors.toSet()));
        assertNotNull(em.find(Vet.class, vetId));
        assertNotNull(em.find(Specialty.class, specialtyId));
    }

    @Test
    void testFindSpecialtiesByVetEmpty() {
        assertTrue(service.findSpecialtiesByVet(vetId).isEmpty());
        assertTrue(service.findVetsBySpecialty(specialtyId).isEmpty());
    }

    @Test
    void testAssignDuplicateShouldFail() {
        service.assign(vetId, specialtyId);
        reload();
        assertThrows(IllegalArgumentException.class, () -> service.assign(vetId, specialtyId));
        assertEquals(1, countAssignment(vetId, specialtyId));
    }

    private long countAssignment(Integer vet, Integer specialty) {
        return ((Number) em.createNativeQuery(
                "select count(*) from vet_specialties where vet_id = :vet and specialty_id = :specialty")
                .setParameter("vet", vet).setParameter("specialty", specialty).getSingleResult()).longValue();
    }
}
