package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.RepositoryLink;
import me.leewonjun.dewminas.domains.compositekeys.RepoLinkPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryLinkRepository extends JpaRepository<RepositoryLink, RepoLinkPk> {
}
