package com.clinic.system.model;

import java.util.List;
import com.clinic.system.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "animal_conditions")
public class AnimalCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exposure_id") // Remove nullable = false so it can link to patient instead
    private Exposure exposure;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id") // Add this to link to the patient_id column seen in your DB
    private Patient patient;

    @Column(name = "condition_name", nullable = false)
    private String conditionName;// healthy, lost/missing, sacrifice, sicked, died, stray

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Exposure getExposure() { return exposure; }
    public void setExposure(Exposure exposure) { this.exposure = exposure; }
    public String getConditionName() { return conditionName; }
    public void setConditionName(String conditionName) { this.conditionName = conditionName; }

}
