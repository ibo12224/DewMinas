package me.leewonjun.dewminas.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.dto.project_sub.TroubleshootingSummary;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "troubleshooting")
public class Troubleshooting implements Updatable<TroubleshootingSummary>{
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Troubleshooting(String problem, String solution,
                           String result, String lesson, Project project) {
        this.problem = problem;
        this.solution = solution;
        this.result = result;
        this.lesson = lesson;
        this.project = project;
    }

    @Override
    public boolean updateData(TroubleshootingSummary summary) {
        boolean res = false;
        if(!this.problem.equals(summary.getProblem())) {
            this.problem = summary.getProblem(); res = true;
        } if(!this.solution.equals(summary.getSolution())) {
            this.solution = summary.getSolution(); res = true;
        } if(!this.result.equals(summary.getResult())) {
            this.result = summary.getResult(); res = true;
        } if(!this.lesson.equals(summary.getLesson())) {
            this.lesson = summary.getLesson(); res = true;
        }
        return res;
    }

    @Override
    public boolean isDifferentWith(Object obj) {
        Troubleshooting troubleshooting = (Troubleshooting) obj;
        return (this.problem.equals(troubleshooting.getProblem()))
                && (this.solution.equals(troubleshooting.getSolution()))
                && (this.result.equals(troubleshooting.getResult()))
                && (this.lesson.equals(troubleshooting.getLesson()));
    }

    @Override
    public void setParent(Object parent) {
        this.setProject((Project) parent);
    }
}

