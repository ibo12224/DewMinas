package me.leewonjun.dewminas.repositories.project_repo;

import me.leewonjun.dewminas.domains.SkillBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillBookRepository extends JpaRepository<SkillBook, Long> {
    Optional<SkillBook> findBySkillName(String skillName);
}
