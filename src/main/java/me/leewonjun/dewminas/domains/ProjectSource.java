package me.leewonjun.dewminas.domains;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.compositekeys.OpenSourceLibsPk;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "open_sources_and_libs")
public class ProjectSource {
    @EmbeddedId
    private OpenSourceLibsPk id;

    @Builder
    public ProjectSource(OpenSourceLibsPk src) {
        this.id = src;
    }
}
