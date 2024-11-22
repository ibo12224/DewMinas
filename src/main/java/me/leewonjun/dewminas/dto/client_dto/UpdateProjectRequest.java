package me.leewonjun.dewminas.dto.client_dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import me.leewonjun.dewminas.domains.ProjectSource;
import me.leewonjun.dewminas.domains.RepositoryLink;
import me.leewonjun.dewminas.domains.Role;
import me.leewonjun.dewminas.dto.project_sub.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateProjectRequest {
    // Update 정보
    // 1. 프로젝트 기본정보 : id, title, shortComment, summary, owner.
    // 2. 프로젝트 항목 요약 정보
    // 1차 개발 제외 요소 : repImage, photos

    private Long id;
    private String title;
    private String shortComment;
    private String summary;

    // owner는 반드시 제거할 것.
    private String owner; // 임의적으로 이메일을 수정하여 공격가능 -> 이후 JWT 적용하여 취약점 개선 예정, SecurityContext에서 Principal 반환->유저 정보 저장됨. 해당 정보이용.

    List<SkillSummary> skills;
    List<TroubleshootingSummary> troubleshooting;
    List<RoleSummary> roles;
    List<RepositoryLinkSummary> repositoryLinks;
    List<ProjectSourceSummary> projectSources;
}
