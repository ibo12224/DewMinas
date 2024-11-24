package me.leewonjun.dewminas.dto.project_sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Skill;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillSummary {
    int capability;
    String skillName;
    String imageUrl;

    public SkillSummary(Skill skill) {
        this.capability = skill.getCapability();
        this.skillName = skill.getSkillName();
        this.imageUrl = skill.getImageUrl();
    }
}
