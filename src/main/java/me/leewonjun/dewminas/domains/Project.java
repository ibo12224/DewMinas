package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.of_resume.Resume;
import me.leewonjun.dewminas.domains.sectiondatefields.CommonDateField;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@Entity(name = "projects")
public class Project extends CommonDateField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column(name = "short_comment")
    private String shortComment;

    @Column
    private String summary;

    @Column(name = "owner", nullable = false)
    private String owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resume_id")
    private Resume resume;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rep_img")
    private ProjectPhoto repImage;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<ProjectPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "id.project", fetch = FetchType.LAZY)
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Troubleshooting> troubleshooting = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "id.project", fetch = FetchType.LAZY)
    private List<RepositoryLink> repositoryLinks = new ArrayList<>();

    @OneToMany(mappedBy = "id.project", fetch = FetchType.LAZY)
    private List<ProjectSource> projectSources = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Builder
    public Project(String title, String shortComment, String summary, Resume resume, String ownerMail) {
        this.title = title;
        this.shortComment = shortComment;
        this.summary = summary;
        this.resume = resume;
        this.owner = ownerMail;
    }
}
