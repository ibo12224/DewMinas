package me.leewonjun.dewminas.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leewonjun.dewminas.domains.*;
import me.leewonjun.dewminas.domains.compositekeys.OpenSourceLibsPk;
import me.leewonjun.dewminas.domains.compositekeys.ProjectSkillPk;
import me.leewonjun.dewminas.domains.compositekeys.RepoLinkPk;
import me.leewonjun.dewminas.dto.client_dto.UpdateProjectRequest;
import me.leewonjun.dewminas.dto.project_sub.*;
import me.leewonjun.dewminas.dto.resume_sub.Specifiable;
import me.leewonjun.dewminas.repositories.project_repo.*;
import me.leewonjun.dewminas.repositories.resume_repo.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final SkillRepository skillRepository;

    @Autowired
    private final TroubleshootingRepository troubleshootingRepository;

    @Autowired
    private final ProjectSourceRepository projectSourceRepository;

    @Autowired
    private final RepositoryLinkRepository repositoryLinkRepository;

    @Autowired
    private final ProjectPhotoRepository projectPhotoRepository;

    @Autowired
    private final ResumeRepository resumeRepository;

    @Autowired
    private final SkillBookRepository skillBookRepository;

    public Project findProject(Long projectId) {
        // 전달 받은 project ID를 이용해 프로젝트 객체를 반환한다.
        // 고려해야할 점?
        return projectRepository.findById(projectId).orElseThrow(()-> new IllegalArgumentException("ProjectService.findProject() : no project id " + projectId));
    }

    public void registerProject(String owner) {
        Project newProject = Project.builder().ownerMail(owner).build();
        newProject.setResume(resumeRepository.findByOwnerEmail(owner).orElseThrow(()-> new IllegalArgumentException("ProjectService.registerProject() : no owner email")));
        projectRepository.save(newProject);
    }

    @Transactional
    public void updateProject(Long id, UpdateProjectRequest request) {
        Project project = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("ProjectService.updateProject() : no project id " + id));
        project.setTitle(request.getTitle());
        project.setSummary(request.getSummary());
        project.setShortComment(request.getShortComment());

        this.<RoleSummary, Role>updateOrSave(project, request.getRoles(), roleRepository,
                (repo, p) -> ((RoleRepository) repo).findByProject(project).stream().map(Updatable::getId).collect(Collectors.toSet()));

        this.<TroubleshootingSummary, Troubleshooting>updateOrSave(project, request.getTroubleshooting(), troubleshootingRepository,
                (repo, p) -> ((TroubleshootingRepository) repo).findByProject(project).stream().map(Updatable::getId).collect(Collectors.toSet()));

        // 나머지 요소 수정중. : project source, skills, ProjectRepository
        projectSourceRepository.deleteAllByProject(project);
        skillRepository.deleteAllByProject(project);
        repositoryLinkRepository.deleteAllByProject(project);

        for(ProjectSourceSummary summary : request.getProjectSources()) {
            ProjectSource source = ProjectSource.builder().src(new OpenSourceLibsPk(project, summary.getSourceName())).build();
            projectSourceRepository.save(source);
        }

        for(SkillSummary summary : request.getSkills()) {
            ProjectSkillPk pk = new ProjectSkillPk(project,
                    skillBookRepository.findBySkillName(summary.getSkillName()).orElseThrow(
                            () -> new IllegalArgumentException("ProjectService.updateProject() : SkillBookRepo findBySkillName() : no such skill name " + summary.getSkillName())
                    ));
            Skill skill = Skill.builder().pk(pk).capability(summary.getCapability()).build();
            // image url은 서버 응답인 경우에만 존재.
            skillRepository.save(skill);
        }

        for(RepositoryLinkSummary summary : request.getRepositoryLinks()) {
            RepoLinkPk pk = new RepoLinkPk(project, summary.getUrl());
            RepositoryLink  link = RepositoryLink.builder().pk(pk).repoType(summary.getRepoType()).build();
            repositoryLinkRepository.save(link);
        }

    }

    private <T extends Specifiable, E extends Updatable<T>> void updateOrSave(
            Project project, List<T> list,
            JpaRepository<E, Long> repository,
            BiFunction<JpaRepository, Project, Set<Long>> getAllIds) // 요청에 없는 요소는 삭제 한다.
    {
        if (Objects.isNull(list)) return;

        Map<Long, E> existEntities = repository.findAllById(
                        list.stream().map(T::getId).toList())
                .stream().collect(Collectors.toMap(E::getId, e -> e));

        Set<Long> allKeys = getAllIds.apply(repository, project);

        for (T item : list) { // dirty checking이 아닌 update JPQL 사용을 고려할 것. 성능 문제.
            E entity = existEntities.getOrDefault(item.getId(), null);
            if (entity != null) {
                entity.updateData(item); // 갱신 메소드 호출 -> 갱신 작업 위임
            } else {
                entity = (E) item.specify();
                entity.setParent(project);
                repository.save(entity); // 새 엔티티 등록작업 리포지토리 인스턴스에 위임.
            }
        }

        allKeys.removeAll(existEntities.keySet());
        repository.deleteAllByIdInBatch(allKeys);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("ProjectService.deleteProject() : no project id "+id)
        );

        projectSourceRepository.deleteAllByProject(project);
        repositoryLinkRepository.deleteAllByProject(project);
        skillRepository.deleteAllByProject(project);
        roleRepository.deleteAllByProject(project);
        troubleshootingRepository.deleteAllByProject(project);
        projectRepository.deleteById(id);
    }
}
