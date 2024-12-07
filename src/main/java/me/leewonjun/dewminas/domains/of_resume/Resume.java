package me.leewonjun.dewminas.domains.of_resume;

import jakarta.persistence.*;
import lombok.*;
import me.leewonjun.dewminas.domains.Project;
import me.leewonjun.dewminas.domains.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"educations", "workExps", "awards", "academicActivities"})
@NoArgsConstructor
@Entity(name = "resumes")
@EntityListeners(value = AuditingEntityListener.class)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    //Owner와 phoneNumber는 고정값. - 수정 페이지에서 제공하지 않음.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private User owner;

    @Column(name = "desired_position")
    private String desiredPosition;
//    @Column(name = "color_scheme", nullable = false)
//    private Long colorScheme;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private ResumePhoto resumePhoto;


    // Automatic auditing되는 컬럼. 신경 X
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Resume( User owner) {
        this.owner = owner;
    }

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<Award> awards = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<EducationalExp> eduExps = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<AcademicActivity> academicActivities = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<WorkExp> workExps = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<License> licenses = new ArrayList<>();

}
