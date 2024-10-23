package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "resumes")
@EntityListeners(value = AuditingEntityListener.class)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name="owner_email")
    private User owner;

//    @Column(name = "color_scheme", nullable = false)
//    private Long colorScheme;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    // foreign key photo_id references resume_photos(id)
    @Column(name = "photo_id")
    private Long photoId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Resume(String phoneNumber, User owner, Long photoId) {
        this.phoneNumber = phoneNumber;
        this.owner = owner;
        this.photoId = photoId;
    }

    @OneToMany(mappedBy = "resume")
    List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    List<Award> awards = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    List<EducationalExp> eduExps = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    List<AcademicActivity> academicActivities = new ArrayList<>();
}
