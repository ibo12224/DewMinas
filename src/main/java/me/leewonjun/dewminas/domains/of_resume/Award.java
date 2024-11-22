package me.leewonjun.dewminas.domains.of_resume;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Updatable;
import me.leewonjun.dewminas.dto.resume_sub.AwardSummary;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "awards")
public class Award implements Updatable<AwardSummary> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name="award_name", nullable = false)
    private String awardName;

    @Column(name="competition_name", nullable = false)
    private String competitionName;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name= "award_date", nullable = false)
    private LocalDateTime awardedDate;

    @ManyToOne
    @JoinColumn(name="resume_id", nullable = false)
    private Resume resume;

    @Builder
    public Award(String awardName, String competitionName, String organizationName, LocalDateTime awardedDate, Resume resume) {
        this.awardName = awardName;
        this.competitionName = competitionName;
        this.organizationName = organizationName;
        this.awardedDate = awardedDate;
        this.resume = resume;
    }

    @Override
    public boolean updateData(AwardSummary awardSummary) {
        boolean res = false;
        if(!this.awardName.equals(awardSummary.getAwardName())) {
            this.awardName = awardSummary.getAwardName(); res = true;
        }
        if(!this.competitionName.equals(awardSummary.getCompetitionName())) {
            this.competitionName = awardSummary.getCompetitionName(); res = true;
        }
        if(!this.organizationName.equals(awardSummary.getOrganizationName())) {
            this.organizationName = awardSummary.getOrganizationName(); res = true;
        }
        if(!this.awardedDate.equals(awardSummary.getAwardedDate())) {
            this.awardedDate = LocalDateTime.of(awardSummary.getAwardedDate().toLocalDate(),
                                                awardSummary.getAwardedDate().toLocalTime()); res = true;
        }
        return res;
    }

    @Override
    public boolean isDifferentWith(Object o) {
        Award other = (Award) o;
        return !(this.awardName.equals(other.getAwardName())
                && this.competitionName.equals(other.getCompetitionName())
                && this.organizationName.equals(other.getOrganizationName())
                && this.awardedDate.toLocalDate().equals(other.getAwardedDate().toLocalDate()));
    }

    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
        if(!(obj instanceof Award)) return false;
        Updatable up = (Updatable)obj;
        return up.getId().equals(this.id);
    }

    @Override
    public void setParent(Object parent) {
        this.setResume((Resume)parent);
    }
}
