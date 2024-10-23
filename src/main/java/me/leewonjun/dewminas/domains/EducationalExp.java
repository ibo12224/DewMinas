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
@Entity(name = "educational_exp")
public class EducationalExp extends CommonDateField{
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
    public EducationalExp(String educationName, String organizationName, LocalDate fromDate,
                          LocalDate toDate, Boolean toNow, Resume resume) {
        this.educationName = educationName;
        this.organizationName = organizationName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.toNow = toNow;
        this.resume = resume;
    }
}
