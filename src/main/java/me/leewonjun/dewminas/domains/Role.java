package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "role_title", nullable = false)
    private String roleTitle;

    @Column(name = "role_comment", nullable = false)
    private String roleComment;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Builder
    public Role(String roleTitle, String roleComment, Project project) {
        this.roleTitle = roleTitle;
        this.roleComment = roleComment;
        this.project = project;
    }
}
