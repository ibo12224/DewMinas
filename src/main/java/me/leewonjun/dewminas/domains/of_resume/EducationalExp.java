package me.leewonjun.dewminas.domains.of_resume;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Updatable;
import me.leewonjun.dewminas.domains.sectiondatefields.CommonDateField;
import me.leewonjun.dewminas.dto.resume_sub.EducationalExpSummary;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "educational_exp")
public class EducationalExp extends CommonDateField implements Updatable<EducationalExpSummary> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "education_name", nullable = false)
    private String educationName;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "to_now", nullable = false)
    private Boolean toNow;

    @ManyToOne
    @JoinColumn(name="resume_id", nullable = false)
    private Resume resume;

    @Builder
    public EducationalExp(String educationName, String organizationName, LocalDateTime fromDate,
                          LocalDateTime toDate, Boolean toNow, Resume resume) {
        this.educationName = educationName;
        this.organizationName = organizationName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.toNow = toNow;
        this.resume = resume;
    }

    @Override
    public boolean updateData(EducationalExpSummary expSummary) {
        boolean res = false;
        if(!this.educationName.equals(expSummary.getEducationName())) {
            this.educationName = expSummary.getEducationName(); res = true;
        }
        if(!this.organizationName.equals(expSummary.getOrganizationName())) {
            this.organizationName = expSummary.getOrganizationName(); res = true;
        }
        if(!this.fromDate.equals(expSummary.getFromDate())) {
            this.fromDate = LocalDateTime.of(
                    expSummary.getFromDate().toLocalDate(),
                    expSummary.getFromDate().toLocalTime()
            ); res = true;
        }
        if(!this.toDate.equals(expSummary.getToDate())) {
            this.toDate = LocalDateTime.of(
                    expSummary.getToDate().toLocalDate(),
                    expSummary.getFromDate().toLocalTime()
            ); res = true;
        }
        if(this.toNow != expSummary.getToNow()) {
            this.toNow = expSummary.getToNow(); res = true;
        }
        return res;
    }

    @Override
    public boolean isDifferentWith(Object o) {
        EducationalExp other = (EducationalExp) o;
        return !(this.educationName.equals(other.getEducationName())
                && this.organizationName.equals(other.getOrganizationName())
                && this.fromDate.toLocalDate().equals(other.getFromDate().toLocalDate())
                && this.toDate.toLocalDate().equals(other.getToDate().toLocalDate())
                && this.toNow.equals(other.getToNow()));
    }

    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
        if(!(obj instanceof EducationalExp)) return false;
        Updatable up = (Updatable)obj;
        return up.getId().equals(this.id);
    }

    @Override
    public void setParent(Object parent) {
        this.setResume((Resume)parent);
    }
}
