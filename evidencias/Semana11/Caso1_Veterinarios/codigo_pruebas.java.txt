package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class VetServiceTest extends DatabaseServiceTest {
    @Autowired private VetService service;

    @Test
    void testCreateVet() {
        Vet saved = service.create(vet());
        assertNotNull(saved.getId());
        reload();
        Vet actual = service.findById(saved.getId());
        assertEquals("Rafael", actual.getFirstName());
        assertEquals("Barrantes", actual.getLastName());
        assertEquals("vet@example.test", actual.getEmail());
        assertEquals("900000001", actual.getPhone());
        assertEquals(true, actual.getActive());
    }

    @Test
    void testUpdateVet() {
        Vet saved = service.create(vet());
        reload();
        saved.setFirstName("Luis");
        saved.setLastName("Gomez");
        saved.setEmail("nuevo@example.test");
        saved.setPhone("900000009");
        saved.setActive(false);
        Vet updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Vet actual = service.findById(saved.getId());
        assertEquals("Luis", actual.getFirstName());
        assertEquals("Gomez", actual.getLastName());
        assertEquals("nuevo@example.test", actual.getEmail());
        assertEquals("900000009", actual.getPhone());
        assertEquals(false, actual.getActive());
    }

    @Test
    void testFindVetById() {
        Vet saved = service.create(vet());
        reload();
        Vet actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals("Rafael", actual.getFirstName());
        assertEquals("Barrantes", actual.getLastName());
        assertEquals("vet@example.test", actual.getEmail());
        assertEquals("900000001", actual.getPhone());
        assertEquals(true, actual.getActive());
    }

    @Test
    void testDeleteVet() {
        Vet saved = service.create(vet());
        reload();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Vet.class, saved.getId()));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindVetByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateVetNotFound() {
        Vet missing = vet();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}
