package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.leewonjun.dewminas.domains.compositekeys.RepoLinkPk;

@Getter
@Setter
@Entity(name = "repository_links")
public class RepositoryLink {

    @EmbeddedId
    private RepoLinkPk id;

    @Column(name = "ropo_type", nullable = false)
    private Integer repoType;

    @Builder
    public RepositoryLink(RepoLinkPk pk, Integer repoType) {
        this.id = pk;
        this.repoType = repoType;
    }

}
