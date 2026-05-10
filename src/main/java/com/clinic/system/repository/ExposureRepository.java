package com.clinic.system.repository;

import com.clinic.system.model.Exposure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExposureRepository extends JpaRepository<Exposure, Long> {

    // Find exposures by clinic ID - FIXED: use Clinic_ClinicId
    List<Exposure> findByClinic_ClinicId(Long clinicId);

    // Find exposures by patient ID - FIXED: use Patient_PatientId
    List<Exposure> findByPatient_PatientId(Long patientId);

    // Alternative: Find single exposure by patient ID - FIXED
    Optional<Exposure> findFirstByPatient_PatientId(Long patientId);

    // Find exposures by patient ID ordered by date - FIXED
    List<Exposure> findByPatient_PatientIdOrderByExposureDateDesc(Long patientId);

    // Custom query to find exposure with patient details
    @Query("SELECT e FROM Exposure e LEFT JOIN FETCH e.patient WHERE e.patient.patientId = :patientId")
    Optional<Exposure> findExposureWithPatientByPatientId(@Param("patientId") Long patientId);
}