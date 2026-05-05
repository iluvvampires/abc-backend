package com.clinic.system.dto;

import java.time.LocalDate;
import java.util.List;

public class RegistrationRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthdate;
    private String gender;
    private String contactNumber;
    private String region;
    private String province;
    private String city;
    private String barangay;
    private String zone;
    private String streetAddress;
    private LocalDate exposureDate;
    private String animalType;
    private String exposureType;      // ADD THIS
    private String placeOfExposure;   // ADD THIS
    private String otherAnimalSpecify; // ADD THIS
    private Long clinicId;


    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getBarangay() { return barangay; }
    public void setBarangay(String barangay) { this.barangay = barangay; }
    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }
    public Long getClinicId() { return clinicId; }
    public void setClinicId(Long clinicId) { this.clinicId = clinicId; }
    public LocalDate getExposureDate() { return exposureDate; }
    public void setExposureDate(LocalDate exposureDate) { this.exposureDate = exposureDate; }
    public String getAnimalType() { return animalType; }
    public void setAnimalType(String animalType) { this.animalType = animalType; }
    public String getExposureType() { return exposureType; }
    public void setExposureType(String exposureType) { this.exposureType = exposureType; }

    public String getPlaceOfExposure() { return placeOfExposure; }
    public void setPlaceOfExposure(String placeOfExposure) { this.placeOfExposure = placeOfExposure; }

    public String getOtherAnimalSpecify() { return otherAnimalSpecify; }
    public void setOtherAnimalSpecify(String otherAnimalSpecify) { this.otherAnimalSpecify = otherAnimalSpecify; }

}

