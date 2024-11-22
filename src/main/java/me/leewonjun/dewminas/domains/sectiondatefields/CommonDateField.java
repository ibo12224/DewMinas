package me.leewonjun.dewminas.domains.sectiondatefields;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class CommonDateField {
    @Column(name = "from_date")
    protected LocalDateTime fromDate;

    @Column(name = "to_date")
    protected  LocalDateTime toDate;

}
