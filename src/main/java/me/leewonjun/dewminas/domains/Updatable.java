package me.leewonjun.dewminas.domains;

import me.leewonjun.dewminas.domains.of_resume.Resume;
import me.leewonjun.dewminas.dto.resume_sub.Specifiable;

public interface Updatable<T extends Specifiable> extends Contrastable {
    boolean updateData(T summary);
    // Lombok @Getter에 의해 오버라이딩
    Long getId();
    void setParent(Object parent);
}
