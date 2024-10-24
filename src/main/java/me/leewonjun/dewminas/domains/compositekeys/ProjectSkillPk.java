package me.leewonjun.dewminas.domains.compositekeys;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Project;
import me.leewonjun.dewminas.domains.SkillBook;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProjectSkillPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private SkillBook skillBook;

    @Override
    public boolean equals(Object key) {
        if(!(key instanceof ProjectSkillPk key2)) return false;
        return key2.getProject().getId().equals(this.getProject().getId())
                && key2.getSkillBook().getId().equals(this.getSkillBook().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getProject(), this.getSkillBook());
    }
}
