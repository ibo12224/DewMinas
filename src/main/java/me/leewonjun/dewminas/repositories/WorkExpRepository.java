package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.WorkExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExpRepository extends JpaRepository<WorkExp, Long> {
}
