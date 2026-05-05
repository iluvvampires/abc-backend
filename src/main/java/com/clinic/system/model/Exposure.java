package com.clinic.system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exposures")
public class Exposure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties({"exposures", "animalConditions"})
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Column(name = "exposure_date", nullable = false)
    private LocalDate exposureDate;

    @Column(name = "place_of_exposure", nullable = false)
    private String placeOfExposure;

    @Column(name = "exposure_type", nullable = false)
    private String exposureType;

    @Column(name = "animal_type", nullable = false)
    private String animalType;

    @Column(name = "bite_category")
    private String biteCategory;

    @OneToMany(mappedBy = "exposure", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnimalCondition> animalConditions = new ArrayList<>();

    // Constructors
    public Exposure() {}

    // Getters - RETURN TYPE must be the data type (String, LocalDate, etc.)
    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public LocalDate getExposureDate() {
        return exposureDate;
    }

    public String getPlaceOfExposure() {
        return placeOfExposure;
    }

    public String getExposureType() {
        return exposureType;
    }

    public String getAnimalType() {
        return animalType;
    }

    public String getBiteCategory() {  // Fixed: was getBitCategory
        return biteCategory;
    }

    public List<AnimalCondition> getAnimalConditions() {
        return animalConditions;
    }

    // Setters - RETURN TYPE is void
    public void setId(Long id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public void setExposureDate(LocalDate exposureDate) {
        this.exposureDate = exposureDate;
    }

    public void setPlaceOfExposure(String placeOfExposure) {
        this.placeOfExposure = placeOfExposure;
    }

    public void setExposureType(String exposureType) {
        this.exposureType = exposureType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public void setBiteCategory(String biteCategory) {  // Fixed: was setBitCategory
        this.biteCategory = biteCategory;
    }

    public void setAnimalConditions(List<AnimalCondition> animalConditions) {
        this.animalConditions = animalConditions;
    }
}