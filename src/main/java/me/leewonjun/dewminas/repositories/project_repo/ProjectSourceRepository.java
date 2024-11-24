package me.leewonjun.dewminas.repositories.project_repo;

import me.leewonjun.dewminas.domains.Project;
import me.leewonjun.dewminas.domains.ProjectSource;
import me.leewonjun.dewminas.domains.compositekeys.OpenSourceLibsPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectSourceRepository extends JpaRepository<ProjectSource, OpenSourceLibsPk> {
    @Modifying
    @Query("delete from open_sources_and_libs o where o.id.project = :project")
    void deleteAllByProject(@Param("project") Project project);

    @Query("select s from open_sources_and_libs s where s.id.project = :project")
    Optional<ProjectSource> findByProject(@Param("project") Project project);
}
