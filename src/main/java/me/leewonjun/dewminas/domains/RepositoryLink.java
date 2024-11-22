package me.leewonjun.dewminas.domains;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.leewonjun.dewminas.domains.compositekeys.RepoLinkPk;

import java.util.Objects;

@Getter
@Setter
@Entity(name = "repository_links")
public class RepositoryLink {

    @EmbeddedId
    private RepoLinkPk id;

    @Column(name = "ropo_type", nullable = false)
    private int repoType; // 0 : github

    @Builder
    public RepositoryLink(RepoLinkPk pk, int repoType) {
        this.id = pk;
        this.repoType = repoType;
    }

    public String getUrl() {
        Objects.requireNonNull(this.getId().getUrl(), "Repo link. getUrl() : no url String");
        return this.id.getUrl();
    }
    // ISP에 근거하여 Updatable을 구현하지 않는다.
    // 복합키를 가지는 엘리먼트는 모두 삭제 후 삽입할 예정.
    // Project가 복합키에 포함되어 있어서 리포지토리 전송이 필요함.
    // 수정 시 기본키 값 수정이 발생 -> 무결성 제약 조건 위배 가능성 있음.
}
