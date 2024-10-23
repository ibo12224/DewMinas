package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "academic_activities")
public class AcademicActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Column(name="academic_institution", nullable = false)
    private String academicInstitution;

    @Column(name = "conference_name")
    private String conferenceName;

    @Column(name = "activity_date", nullable = false)
    private LocalDate activityDate;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Builder
    public AcademicActivity(String activityName,String academicInstitution, String conferenceName, LocalDate activityDate, Resume resume) {
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.academicInstitution = academicInstitution;
        this.conferenceName = conferenceName;
        this.resume = resume;
    }
}
