package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@Transactional
abstract class DatabaseServiceTest {
    @Autowired protected EntityManager em;

    protected void reload() {
        em.flush();
        em.clear();
    }

    protected Vet vet() {
        Vet value = new Vet();
        value.setFirstName("Rafael"); value.setLastName("Barrantes");
        value.setEmail("vet@example.test"); value.setPhone("900000001"); value.setActive(true);
        return value;
    }

    protected Owner owner() {
        Owner value = new Owner();
        value.setFirstName("Ana"); value.setLastName("Perez");
        value.setAddress("Av. Lima 100"); value.setCity("Lima"); value.setTelephone("900000002");
        return value;
    }

    protected Specialty specialty() {
        Specialty value = new Specialty();
        value.setName("Cardiologia S11"); value.setOffice("A101");
        value.setHOpen(8); value.setHClose(17);
        return value;
    }

    protected Type type() {
        Type value = new Type();
        value.setName("Tipo S11"); value.setDescription("Tipo de prueba"); value.setActive(true);
        value.setSizeCategory("small"); value.setAverageLifespan(10); value.setCareLevel("medium");
        return value;
    }

    protected Visit visit() {
        Owner owner = owner(); em.persist(owner);
        Type type = type(); em.persist(type);
        Pet pet = new Pet(null, "Mascota S11", type.getId(), owner.getId(), LocalDate.of(2024, 1, 1));
        em.persist(pet);
        Vet vet = vet(); em.persist(vet);
        Visit value = new Visit(); value.setPet(pet); value.setVet(vet);
        value.setVisitDate(LocalDate.of(2026, 11, 6)); value.setDescription("Consulta S11");
        value.setCost(new BigDecimal("75.50"));
        return value;
    }
}
