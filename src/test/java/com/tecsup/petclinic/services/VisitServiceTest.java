package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class VisitServiceTest extends DatabaseServiceTest {
    @Autowired private VisitService service;

    @Test
    void testCreateVisit() {
        Visit saved = service.create(visit());
        Integer petId = saved.getPet().getId();
        Integer ownerId = saved.getPet().getOwnerId();
        Integer vetId = saved.getVet().getId();
        assertNotNull(saved.getId());
        reload();
        Visit actual = service.findById(saved.getId());
        assertEquals(java.time.LocalDate.of(2026, 11, 6), actual.getVisitDate());
        assertEquals("Consulta S11", actual.getDescription());
        assertEquals(new java.math.BigDecimal("75.50"), actual.getCost());
        assertEquals(petId, actual.getPet().getId());
        assertEquals(ownerId, actual.getPet().getOwnerId());
        assertEquals(vetId, actual.getVet().getId());
    }

    @Test
    void testUpdateVisit() {
        Visit saved = service.create(visit());
        reload();
        Visit newRelations = visit();
        saved.setPet(newRelations.getPet());
        saved.setVet(newRelations.getVet());
        Integer petId = newRelations.getPet().getId();
        Integer ownerId = newRelations.getPet().getOwnerId();
        Integer vetId = newRelations.getVet().getId();
        saved.setVisitDate(java.time.LocalDate.of(2026, 11, 7));
        saved.setDescription("Control S11");
        saved.setCost(new java.math.BigDecimal("100.25"));
        Visit updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Visit actual = service.findById(saved.getId());
        assertEquals(java.time.LocalDate.of(2026, 11, 7), actual.getVisitDate());
        assertEquals("Control S11", actual.getDescription());
        assertEquals(new java.math.BigDecimal("100.25"), actual.getCost());
        assertEquals(petId, actual.getPet().getId());
        assertEquals(ownerId, actual.getPet().getOwnerId());
        assertEquals(vetId, actual.getVet().getId());
    }

    @Test
    void testFindVisitById() {
        Visit saved = service.create(visit());
        reload();
        Visit actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals(java.time.LocalDate.of(2026, 11, 6), actual.getVisitDate());
        assertEquals("Consulta S11", actual.getDescription());
        assertEquals(new java.math.BigDecimal("75.50"), actual.getCost());
    }

    @Test
    void testDeleteVisit() {
        Visit saved = service.create(visit());
        reload();
        Integer petId = saved.getPet().getId();
        Integer ownerId = saved.getPet().getOwnerId();
        Integer vetId = saved.getVet().getId();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Visit.class, saved.getId()));
        assertNotNull(em.find(com.tecsup.petclinic.entities.Pet.class, petId));
        assertNotNull(em.find(com.tecsup.petclinic.entities.Owner.class, ownerId));
        assertNotNull(em.find(com.tecsup.petclinic.entities.Vet.class, vetId));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindVisitByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateVisitNotFound() {
        Visit missing = visit();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}
