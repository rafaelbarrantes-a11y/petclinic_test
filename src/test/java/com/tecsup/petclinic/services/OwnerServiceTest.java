package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceTest extends DatabaseServiceTest {
    @Autowired private OwnerService service;

    @Test
    void testCreateOwner() {
        Owner saved = service.create(owner());
        assertNotNull(saved.getId());
        reload();
        Owner actual = service.findById(saved.getId());
        assertEquals("Ana", actual.getFirstName());
        assertEquals("Perez", actual.getLastName());
        assertEquals("Av. Lima 100", actual.getAddress());
        assertEquals("Lima", actual.getCity());
        assertEquals("900000002", actual.getTelephone());
    }

    @Test
    void testUpdateOwner() {
        Owner saved = service.create(owner());
        reload();
        saved.setFirstName("Maria");
        saved.setLastName("Lopez");
        saved.setAddress("Calle Sur 20");
        saved.setCity("Arequipa");
        saved.setTelephone("900000008");
        Owner updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Owner actual = service.findById(saved.getId());
        assertEquals("Maria", actual.getFirstName());
        assertEquals("Lopez", actual.getLastName());
        assertEquals("Calle Sur 20", actual.getAddress());
        assertEquals("Arequipa", actual.getCity());
        assertEquals("900000008", actual.getTelephone());
    }

    @Test
    void testFindOwnerById() {
        Owner saved = service.create(owner());
        reload();
        Owner actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals("Ana", actual.getFirstName());
        assertEquals("Perez", actual.getLastName());
        assertEquals("Av. Lima 100", actual.getAddress());
        assertEquals("Lima", actual.getCity());
        assertEquals("900000002", actual.getTelephone());
    }

    @Test
    void testDeleteOwner() {
        Owner saved = service.create(owner());
        reload();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Owner.class, saved.getId()));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindOwnerByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateOwnerNotFound() {
        Owner missing = owner();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}
