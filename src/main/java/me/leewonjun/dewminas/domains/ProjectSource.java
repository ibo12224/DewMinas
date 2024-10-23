package me.leewonjun.dewminas.domains;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "open_sources_and_libs")
public class ProjectSource {
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Id
    @Column(name = "source_name")
    private String sourceName;

    @Builder
    public ProjectSource(Project project, String sourceName) {
        this.project = project;
        this.sourceName = sourceName;
    }
}
