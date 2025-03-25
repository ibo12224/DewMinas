package me.leewonjun.dewminas.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leewonjun.dewminas.domains.*;
import me.leewonjun.dewminas.domains.of_resume.*;
import me.leewonjun.dewminas.dto.client_dto.RegisterResumeRequest;
import me.leewonjun.dewminas.dto.client_dto.UpdateResumeRequest;
import me.leewonjun.dewminas.dto.resume_sub.Specifiable;
import me.leewonjun.dewminas.repositories.*;
import me.leewonjun.dewminas.repositories.project_repo.ProjectRepository;
import me.leewonjun.dewminas.repositories.resume_repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {
    @Autowired
    private final ResumeRepository resumeRepository;

    @Autowired
    private final UserRepository userRepository;

    // 일반 이력서 항목 리포지토리(퍼시스턴스 계층)
    @Autowired
    private final AcademicActivityRepository academicActivityRepository;

    @Autowired
    private final AwardRepository awardRepository;

    @Autowired
    private final EducationalExpRepository educationalExpRepository;

    @Autowired
    private final EducationRepository educationRepository;

    @Autowired
    private final LicenseRepository licenseRepository;

    @Autowired
    private final WorkExpRepository workExpRepository;

    @Autowired
    private final ProjectRepository projectRepository;

    public String getOwnerEmailById(Long resume_id) {
        return resumeRepository.findById(resume_id).orElseThrow(()->new IllegalArgumentException("Resume id '"+resume_id+"' does not exist.")).getOwner().getEmail();
    }

    public Resume findResume(String email) {
        User owner = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("No such email exists"));
        return resumeRepository.findByOwner(owner).orElseThrow(()->new IllegalArgumentException("No such owner exists"));
    }

    public void registerResume(RegisterResumeRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new IllegalArgumentException("no such user email"));
        Resume resume = Resume.builder().owner(user).build();
        resumeRepository.save(resume);
    }

    public void updateResumeBeforeFlush(Long id, UpdateResumeRequest request) {
        this.updateResume(id, request);
        resumeRepository.flush();
    }

    @Transactional
    public void updateResume(Long id, UpdateResumeRequest request) {
        Resume resume = resumeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("no such resume id : " + id));
        resume.setDesiredPosition(request.getDesiredPosition()); // Dirty checking
        
        // 갱신 또는 추가, 삭제 수행 메소드
        updateOrSave(resume, request.getAcademicActivities(), academicActivityRepository,
                (repo, oresume) -> ((AcademicActivityRepository)repo).findByResume(oresume).stream().map(AcademicActivity::getId).collect(Collectors.toSet()));
        updateOrSave(resume, request.getAwards(), awardRepository,
                (repo, oresume) -> ((AwardRepository)repo).findByResume(oresume).stream().map(Award::getId).collect(Collectors.toSet()));
        updateOrSave(resume, request.getEducations(), educationRepository,
                (repo, oresume) -> ((EducationRepository)repo).findByResume(oresume).stream().map(Education::getId).collect(Collectors.toSet()));
        updateOrSave(resume, request.getEduExps(), educationalExpRepository,
                (repo, oresume) -> ((EducationalExpRepository)repo).findByResume(resume).stream().map(EducationalExp::getId).collect(Collectors.toSet()));
        updateOrSave(resume, request.getLicenses(), licenseRepository,
                (repo, oresume) -> ((LicenseRepository)repo).findByResume(resume).stream().map(License::getId).collect(Collectors.toSet()));
        updateOrSave(resume, request.getWorkExps(), workExpRepository,
                (repo, oresume) -> ((WorkExpRepository)repo).findByResume(oresume).stream().map(WorkExp::getId).collect(Collectors.toSet()));
    }

    // 갱신 또는 삽입을 결정 하는 메소드
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T extends Specifiable, E extends Updatable<T>> void updateOrSave(
            Resume resume, List<T> list,
            JpaRepository<E, Long> repository,
            BiFunction<JpaRepository, Resume, Set<Long>> getAllIds) // 요청에 없는 요소는 삭제 한다.
    {
        if (Objects.isNull(list)) return; // null이면 바로 종료
        // 전달 받은 요소별 리스트에서 ID를 추출하고, 해당 ID의 엔티티들을 불러온다.
        Map<Long, E> existEntities = repository.findAllById(
                        list.stream().map(T::getId).toList())
                .stream().collect(Collectors.toMap(E::getId, e -> e));
        // 새로운 튜플이 추가되기 전에 해야함. (list에는 현재 id 없는 컬럼들이 있고, 이들을 후에 삭제해버리기 때문.)
        Set<Long> allKeys = getAllIds.apply(repository, resume);

        for (T item : list) { // dirty checking이 아닌 update JPQL 사용을 고려할 것. 성능 문제.
            E entity = existEntities.getOrDefault(item.getId(), null);
            if (entity != null) {
                entity.updateData(item); // 갱신 메소드 호출 -> 갱신 작업 위임
            } else {
                entity = (E) item.specify();
                entity.setParent(resume);
                repository.save(entity); // 새 엔티티 등록작업 리포지토리 인스턴스에 위임.
            }
        }

        // 현재 엔티티 테이블에서 현재 이력서를 참조하는 모든 엔티티의 id값을 set으로 불러온다.
        allKeys.removeAll(existEntities.keySet());
        repository.deleteAllByIdInBatch(allKeys);
    }

    @Transactional
    public void deleteResume(String email) {
        User owner = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("deleteResume : "+ email + " does not exist."));
        Resume resume = resumeRepository.findByOwner(owner).orElseThrow(()->new UnsupportedOperationException("deleteResume : "+ email + " didn't register any resumes"));

        academicActivityRepository.deleteAllByResume(resume);
        awardRepository.deleteAllByResume(resume);
        educationRepository.deleteAllByResume(resume);
        educationalExpRepository.deleteAllByResume(resume);
        licenseRepository.deleteAllByResume(resume);
        workExpRepository.deleteAllByResume(resume);

        List<Project> projects = projectRepository.findAllByResume(resume);
        for(Project project : projects) project.setResume(null);
        resumeRepository.deleteById(resume.getId());
    }
}
