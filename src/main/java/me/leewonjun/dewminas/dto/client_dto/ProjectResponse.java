package me.leewonjun.dewminas.dto.client_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Project;
import me.leewonjun.dewminas.domains.ProjectSource;
import me.leewonjun.dewminas.dto.project_sub.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String title;
    private String summary;
    private String email;

    private List<RoleSummary> roles;
    private List<TroubleshootingSummary> troubleshooting;
    private List<SkillSummary> skills;
    private List<ProjectSourceSummary> projectSources;
    private List<RepositoryLinkSummary> repositoryLinks;

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.summary = project.getSummary();
        this.email = project.getOwner();

        this.roles =
                (project.getRoles() == null) ? null : project.getRoles().stream().map(RoleSummary::new).toList();
        this.troubleshooting =
                (project.getTroubleshooting() == null) ? null : project.getTroubleshooting().stream().map(TroubleshootingSummary::new).toList();
        this.skills =
                (project.getSkills() == null) ? null : project.getSkills().stream().map(SkillSummary::new).toList();
        this.projectSources =
                (project.getProjectSources() == null) ? null : project.getProjectSources().stream().map(ProjectSourceSummary::new).toList();
        this.repositoryLinks =
                (project.getRepositoryLinks() == null) ? null : project.getRepositoryLinks().stream().map(RepositoryLinkSummary::new).toList();
    }
}
