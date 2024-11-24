package me.leewonjun.dewminas.repositories.project_repo;

import me.leewonjun.dewminas.domains.Project;
import me.leewonjun.dewminas.domains.compositekeys.ProjectSkillPk;
import me.leewonjun.dewminas.domains.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, ProjectSkillPk> {
    @Modifying
    @Query("delete from skills s where s.id.project = :project")
    void deleteAllByProject(@Param("project")  Project project);
}
