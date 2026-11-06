package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "vet_specialties")
@Getter
@Setter
public class VetSpecialty {
    @EmbeddedId
    private VetSpecialtyId id;
    @MapsId("vetId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vet_id")
    private Vet vet;
    @MapsId("specialtyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;
    private LocalDate certificationDate;
    private Integer yearsExperience;
    private Boolean isPrimary;
    private String notes;
}
