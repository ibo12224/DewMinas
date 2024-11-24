package me.leewonjun.dewminas.dto.project_sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.RepositoryLink;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryLinkSummary {
    private int repoType;
    private String url;
    
    // 생성자 = 엔티티와 그 요약 객체의 공변성 제공.(not polymorphism);
    public RepositoryLinkSummary(RepositoryLink repositoryLink) {
        this.repoType = repositoryLink.getRepoType();
        this.url = repositoryLink.getUrl();
    }
    // ISP에 근거하여 Specifiable을 구현하지 않는다.
    // 복합키를 가지는 엘리먼트는 모두 삭제 후 삽입할 예정.
    // Project가 복합키에 포함되어 있어서 리포지토리 전송이 필요함.
    // 수정 시 기본키 값 수정이 발생 -> 무결성 제약 조건 위배 가능성 있음.
}
