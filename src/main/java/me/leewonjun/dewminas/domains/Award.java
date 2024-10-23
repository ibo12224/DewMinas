package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "awards")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name="award_name", nullable = false)
    private String awardName;

    @Column(name="competition_name", nullable = false)
    private String competitionName;

    @Column(name = "Organization_name", nullable = false)
    private String organizationName;

    @Column(name= "award_date", nullable = false)
    private LocalDate award_date;

    @ManyToOne
    @JoinColumn(name="resume_id", nullable = false)
    private Resume resume;

    @Builder
    public Award(String awardName, String competitionName, String organizationName, LocalDate award_date, Resume resume) {
        this.awardName = awardName;
        this.competitionName = competitionName;
        this.organizationName = organizationName;
        this.award_date = award_date;
        this.resume = resume;
    }
}
