package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "skills")
public class Skill {
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Id
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private  SkillBook skillInfo;

    @Column(name = "capability")
    private Byte capability;

    @Builder
    public Skill(Project project, SkillBook skillBook, Byte capability) {
        this.project = project;
        this.skillInfo = skillBook;
        this.capability = capability;
    }
}
