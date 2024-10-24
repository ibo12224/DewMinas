package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
}
