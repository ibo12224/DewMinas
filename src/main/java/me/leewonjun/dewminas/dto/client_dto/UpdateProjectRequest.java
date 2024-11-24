package me.leewonjun.dewminas.dto.client_dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.dto.project_sub.*;

import java.util.List;

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


/*
{
    "id" : 1,
    "title" : "Dewminas",
    "shortComment" : "Dewminas, 개발자 이력서 작성 사이트",
    "summary" : "이력서를 손쉽게 작성할 수 있는 정형적인 템플릿을 제공합니다.",
    "owner" : "mail@gmail.com",
    "skills" : [
        {
        "capability" : 3,
        "skillName" : "springboot",
        "imageUrl" : null
        }
    ],
    "troubleshooting" : [
        {
        "id" : null,
        "problem" : "복합키 속성 수정에 의한 개체 무결성 제약조건 위배",
        "solution" : "수정 연산 적용 방식을 삭제 후 삽입으로 변경",
        "result" : "제약조건 위배사항 발생 X",
        "lesson" : "DB 설계시 자주 바뀌는 속성은 기본키로 포함시키지 말 것."
        }
    ],
    "roles" : [
        {
        "id" : null,
        "roleTitle" : "팀장",
        "roleComment" : "프로젝트 관리, 서버 개발 담당, 클라이언트 개발 관리"
        }
    ],
    "repositoryLinks" : [
        {
        "repoType" : 1,
        "url" : "http://localhost:8080"
        }
    ],
    "projectSources" : [
        {
        "sourceName" : "spring-security"
        }
    ]
}

*/