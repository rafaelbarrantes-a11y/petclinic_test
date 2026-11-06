package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceTest extends DatabaseServiceTest {
    @Autowired private SpecialtyService service;

    @Test
    void testCreateSpecialty() {
        Specialty saved = service.create(specialty());
        assertNotNull(saved.getId());
        reload();
        Specialty actual = service.findById(saved.getId());
        assertEquals("Cardiologia S11", actual.getName());
        assertEquals("A101", actual.getOffice());
        assertEquals(8, actual.getHOpen());
        assertEquals(17, actual.getHClose());
    }

    @Test
    void testUpdateSpecialty() {
        Specialty saved = service.create(specialty());
        reload();
        saved.setName("Neurologia S11");
        saved.setOffice("B202");
        saved.setHOpen(9);
        saved.setHClose(18);
        Specialty updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Specialty actual = service.findById(saved.getId());
        assertEquals("Neurologia S11", actual.getName());
        assertEquals("B202", actual.getOffice());
        assertEquals(9, actual.getHOpen());
        assertEquals(18, actual.getHClose());
    }

    @Test
    void testFindSpecialtyById() {
        Specialty saved = service.create(specialty());
        reload();
        Specialty actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals("Cardiologia S11", actual.getName());
        assertEquals("A101", actual.getOffice());
        assertEquals(8, actual.getHOpen());
        assertEquals(17, actual.getHClose());
    }

    @Test
    void testDeleteSpecialty() {
        Specialty saved = service.create(specialty());
        reload();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Specialty.class, saved.getId()));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindSpecialtyByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateSpecialtyNotFound() {
        Specialty missing = specialty();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}
