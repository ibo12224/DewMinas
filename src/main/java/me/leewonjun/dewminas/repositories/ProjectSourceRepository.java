package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.ProjectSource;
import me.leewonjun.dewminas.domains.compositekeys.OpenSourceLibsPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSourceRepository extends JpaRepository<ProjectSource, OpenSourceLibsPk> {
}
