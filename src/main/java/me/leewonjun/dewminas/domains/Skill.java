package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.compositekeys.ProjectSkillPk;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "skills")
public class Skill {
    @EmbeddedId
    private ProjectSkillPk id;

    @Column(name = "capability")
    private int capability;

    @Builder
    public Skill(ProjectSkillPk pk, int capability) {
        this.id = pk;
        this.capability = capability;
    }

    public String getSkillName() {
        Objects.requireNonNull(this.getId().getSkillBook(), "Skill.getSkillName() : no skill book");
        return this.id.getSkillBook().getSkillName();
    }

    public String getImageUrl() {
        Objects.requireNonNull(this.id.getSkillBook(), "Skill.getSkillName() : no skill book");
        return this.id.getSkillBook().getImageUrl();
    }
}
