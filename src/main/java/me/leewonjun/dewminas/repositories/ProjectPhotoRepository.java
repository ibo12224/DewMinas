package me.leewonjun.dewminas.repositories;

import me.leewonjun.dewminas.domains.ProjectPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPhotoRepository extends JpaRepository<ProjectPhoto, Long> {
}
