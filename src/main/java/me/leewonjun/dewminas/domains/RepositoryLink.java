package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "repository_links")
public class RepositoryLink {
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "ropo_type", nullable = false)
    private Integer repoType;

    @Id
    @Column(nullable = false)
    private String url;

    @Builder
    public RepositoryLink(Project project, String url, Integer repoType) {
        this.project = project;
        this.url = url;
        this.repoType = repoType;
    }
}
