package me.leewonjun.dewminas.repositories.project_repo;

import me.leewonjun.dewminas.domains.Project;
import me.leewonjun.dewminas.domains.Troubleshooting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TroubleshootingRepository extends JpaRepository<Troubleshooting, Long> {
    List<Troubleshooting> findByProject(Project project);
    void deleteAllByProject(Project project);
}
