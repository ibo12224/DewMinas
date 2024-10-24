package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.AcademicActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicActivityRepository extends JpaRepository<AcademicActivity, Long> {
}
