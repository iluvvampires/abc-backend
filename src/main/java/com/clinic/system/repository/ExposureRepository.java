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

    // Find exposures by clinic ID
    List<Exposure> findByClinicId(Long clinicId);

    // Find exposures by patient ID - RETURNS LIST (not Optional)
    List<Exposure> findByPatientId(Long patientId);

    // Alternative: Find single exposure by patient ID (if one-to-one relationship)
    Optional<Exposure> findFirstByPatientId(Long patientId);

    // Find exposures by patient ID ordered by date (most recent first)
    List<Exposure> findByPatientIdOrderByExposureDateDesc(Long patientId);

    // Custom query to find exposure with patient details
    @Query("SELECT e FROM Exposure e LEFT JOIN FETCH e.patient WHERE e.patient.id = :patientId")
    Optional<Exposure> findExposureWithPatientByPatientId(@Param("patientId") Long patientId);
}