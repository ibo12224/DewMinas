package me.leewonjun.dewminas.dto.project_sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Troubleshooting;
import me.leewonjun.dewminas.domains.Updatable;
import me.leewonjun.dewminas.dto.resume_sub.Specifiable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TroubleshootingSummary implements Specifiable {
    private Long id;
    private String problem;
    private String solution;
    private String result;
    private String lesson;

    public TroubleshootingSummary(Troubleshooting troubleshooting) {
        this.id = troubleshooting.getId();
        this.problem = troubleshooting.getProblem();
        this.solution = troubleshooting.getSolution();
        this.result = troubleshooting.getResult();
        this.lesson = troubleshooting.getLesson();
    }

    @Override
    public Updatable<? extends Specifiable> specify() {
        return Troubleshooting.builder()
                .problem(this.problem)
                .result(this.result)
                .solution(this.solution)
                .lesson(this.lesson)
                .build();
    }
}
