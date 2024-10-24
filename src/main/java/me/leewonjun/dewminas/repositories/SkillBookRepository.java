package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.SkillBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillBookRepository extends JpaRepository<SkillBook, Long> {
}
