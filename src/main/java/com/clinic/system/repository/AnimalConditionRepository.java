package com.clinic.system.repository;

import com.clinic.system.model.AnimalCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalConditionRepository extends JpaRepository<AnimalCondition, Long> {
}
