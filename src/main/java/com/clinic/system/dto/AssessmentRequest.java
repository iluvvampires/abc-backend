package com.clinic.system.dto;

import java.time.LocalDate;
import java.util.List;

public class AssessmentRequest {
    private LocalDate exposureDate;
    private String animalType;
    private String biteCategory;
    private String exposureType;
    private String placeOfExposure;
    private List<String> animalConditions;

    // Constructors
    public AssessmentRequest() {}

    // Getters
    public LocalDate getExposureDate() { return exposureDate; }
    public String getAnimalType() { return animalType; }
    public String getBiteCategory() { return biteCategory; }
    public String getExposureType() { return exposureType; }
    public String getPlaceOfExposure() { return placeOfExposure; }
    public List<String> getAnimalConditions() { return animalConditions; }

    // Setters
    public void setExposureDate(LocalDate exposureDate) { this.exposureDate = exposureDate; }
    public void setAnimalType(String animalType) { this.animalType = animalType; }
    public void setBiteCategory(String biteCategory) { this.biteCategory = biteCategory; }
    public void setExposureType(String exposureType) { this.exposureType = exposureType; }
    public void setPlaceOfExposure(String placeOfExposure) { this.placeOfExposure = placeOfExposure; }
    public void setAnimalConditions(List<String> animalConditions) { this.animalConditions = animalConditions; }
}