package me.leewonjun.dewminas.repositories.resume_repo;

import me.leewonjun.dewminas.domains.of_resume.Resume;
import me.leewonjun.dewminas.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByOwner(User owner);

    @Query("select r from resumes r where r.owner.email = :email")
    Optional<Resume> findByOwnerEmail(@Param("email") String mail);
}
