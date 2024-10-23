package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "troubleshootings")
public class Troubleshooting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "problem", nullable = false)
    private String problem;

    @Column(name = "solution", nullable = false)
    private String solution;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "lesson")
    private String lesson;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Troubleshooting(String problem, String solution, String result, String lesson, Project project) {
        this.problem = problem;
        this.solution = solution;
        this.result = result;
        this.lesson = lesson;
        this.project = project;
    }
}
