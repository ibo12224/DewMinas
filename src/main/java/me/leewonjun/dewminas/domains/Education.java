package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.sectiondatefields.CommonDateField;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity(name="educations")
public class Education extends CommonDateField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "type", nullable = false)
    private Short type;

    @Column(name="institution_name", nullable = false)
    private String institutionName;

    @Column(name="department_name", nullable = false)
    private String departmentName;

    @Column(name = "degree")
    private String degree;

    @Column(name="gpa")
    private Double gpa;

    @Column(name="max_gpa")
    private Double maxGpa;

    @Column(name = "to_now", nullable = false)
    private Boolean toNow;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Builder
    public Education(Short type, String institutionName, String departmentName, String degree, Double gpa,
                     Double maxGpa, LocalDate fromDate, LocalDate toDate, Boolean toNow, Resume resume) {
        this.type = type;
        this.institutionName = institutionName;
        this.departmentName = departmentName;
        this.degree = degree;
        this.gpa = gpa;
        this.maxGpa = maxGpa;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.toNow = toNow;
    }

}
