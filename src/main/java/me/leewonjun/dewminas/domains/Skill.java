package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.compositekeys.ProjectSkillPk;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "skills")
public class Skill {
    @EmbeddedId
    private ProjectSkillPk id;

    @Column(name = "capability")
    private Byte capability;

    @Builder
    public Skill(ProjectSkillPk pk, Byte capability) {
        this.id = pk;
        this.capability = capability;
    }
}
