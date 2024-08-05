package com.prescriptions.taks.prescriptions.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User{
    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private Set<Prescription> prescriptions;
    private String specialty;

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
