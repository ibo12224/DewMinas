package me.leewonjun.dewminas.domains.of_resume;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Updatable;
import me.leewonjun.dewminas.dto.resume_sub.AcademicActivitySummary;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "academic_activities")
public class AcademicActivity implements Updatable<AcademicActivitySummary> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Column(name="institution_name", nullable = false)
    private String institutionName;

    @Column(name = "conference_name")
    private String conferenceName;

    @Column(name = "activity_date", nullable = false)
    private LocalDateTime activityDate;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Builder
    public AcademicActivity(String activityName,String academicInstitution, String conferenceName, LocalDateTime activityDate, Resume resume) {
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.institutionName = academicInstitution;
        this.conferenceName = conferenceName;
        this.resume = resume;
    }

    @Override
    public boolean updateData(AcademicActivitySummary summary) {
        boolean res = false;

        if(!this.activityName.equals(summary.getActivityName())) {
            this.activityName = summary.getActivityName(); res = true;
        }
        if(!this.activityDate.equals(summary.getActivityDate())) {
            this.activityDate = LocalDateTime.of(
                    summary.getActivityDate().toLocalDate(),
                    summary.getActivityDate().toLocalTime()); res = true;
        }
        if(!this.institutionName.equals(summary.getInstitutionName())) {
            this.institutionName = summary.getInstitutionName();  res = true;
        }
        if(!this.conferenceName.equals(summary.getConferenceName())) {
            this.conferenceName = summary.getConferenceName(); res = true;
        }

        return res;
    }

    @Override
    public boolean isDifferentWith(Object o) {
        AcademicActivity other = (AcademicActivity) o;
        return !(this.activityName.equals(other.getActivityName())
        && this.activityDate.equals(other.getActivityDate())
        && this.institutionName.equals(other.getInstitutionName())
        && this.conferenceName.equals(other.getConferenceName()));
    }

    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
        if(!(obj instanceof AcademicActivity)) return false;
        Updatable up = (Updatable)obj;
        return up.getId().equals(this.id);
    }

    @Override
    public void setParent(Object parent) {
        this.setResume((Resume)parent);
    }
}
