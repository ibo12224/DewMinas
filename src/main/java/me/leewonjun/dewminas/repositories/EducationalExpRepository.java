package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.EducationalExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalExpRepository extends JpaRepository<EducationalExp, Long> {
}
