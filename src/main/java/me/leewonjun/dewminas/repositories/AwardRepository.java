package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
}
