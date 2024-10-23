package me.leewonjun.dewminas.domains;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class CommonDateField {
    @Column(name = "from_date", nullable = false)
    protected LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    protected  LocalDate toDate;

}
