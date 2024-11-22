package me.leewonjun.dewminas.repositories.project_repo;

import me.leewonjun.dewminas.domains.Project;
import me.leewonjun.dewminas.domains.RepositoryLink;
import me.leewonjun.dewminas.domains.compositekeys.RepoLinkPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryLinkRepository extends JpaRepository<RepositoryLink, RepoLinkPk> {
    @Query("delete from repository_links l where l.id.project = :project")
    void deleteAllByProject(@Param("project") Project project);
}
