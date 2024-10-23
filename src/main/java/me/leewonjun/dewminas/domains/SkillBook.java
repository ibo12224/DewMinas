package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "skill_book")
public class SkillBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "img_url")
    private String imageUrl;

}
