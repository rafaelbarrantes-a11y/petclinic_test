package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class TypeServiceTest extends DatabaseServiceTest {
    @Autowired private TypeService service;

    @Test
    void testCreateType() {
        Type saved = service.create(type());
        assertNotNull(saved.getId());
        reload();
        Type actual = service.findById(saved.getId());
        assertEquals("Tipo S11", actual.getName());
        assertEquals("Tipo de prueba", actual.getDescription());
        assertEquals(true, actual.getActive());
        assertEquals("small", actual.getSizeCategory());
        assertEquals(10, actual.getAverageLifespan());
        assertEquals("medium", actual.getCareLevel());
    }

    @Test
    void testUpdateType() {
        Type saved = service.create(type());
        reload();
        saved.setName("Tipo actualizado S11");
        saved.setDescription("Descripcion actualizada");
        saved.setActive(false);
        saved.setSizeCategory("medium");
        saved.setAverageLifespan(12);
        saved.setCareLevel("high");
        Type updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Type actual = service.findById(saved.getId());
        assertEquals("Tipo actualizado S11", actual.getName());
        assertEquals("Descripcion actualizada", actual.getDescription());
        assertEquals(false, actual.getActive());
        assertEquals("medium", actual.getSizeCategory());
        assertEquals(12, actual.getAverageLifespan());
        assertEquals("high", actual.getCareLevel());
    }

    @Test
    void testFindTypeById() {
        Type saved = service.create(type());
        reload();
        Type actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals("Tipo S11", actual.getName());
        assertEquals("Tipo de prueba", actual.getDescription());
        assertEquals(true, actual.getActive());
        assertEquals("small", actual.getSizeCategory());
        assertEquals(10, actual.getAverageLifespan());
        assertEquals("medium", actual.getCareLevel());
    }

    @Test
    void testDeleteType() {
        Type saved = service.create(type());
        reload();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Type.class, saved.getId()));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindTypeByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateTypeNotFound() {
        Type missing = type();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}
