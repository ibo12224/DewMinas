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
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OpenSourceLibsPk implements Serializable {
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @Column(name = "source_name")
    private String sourceName;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof OpenSourceLibsPk key2)) return false;
        return (key2.getProject().getId().equals(this.project.getId())) &&
                (key2.getSourceName().equals(this.sourceName));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.project, this.sourceName);
    }
}
