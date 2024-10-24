package me.leewonjun.dewminas.domains.compositekeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Project;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RepoLinkPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "url")
    private  String url;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof RepoLinkPk key)) return false;
        return key.getProject().getId().equals(this.project.getId()) &&
                key.getUrl().equals(this.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, url);
    }
}
