package me.leewonjun.dewminas.domains.of_resume;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Updatable;
import me.leewonjun.dewminas.dto.resume_sub.LicenseSummary;

import java.time.LocalDateTime;

@Entity(name = "licenses")
@Getter
@Setter
@NoArgsConstructor
public class License implements Updatable<LicenseSummary> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "license_name", nullable = false)
    private String licenseName;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issuedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public License(String licenseName, String organizationName, LocalDateTime issuedAt) {
        this.licenseName = licenseName;
        this.organizationName = organizationName;
        this.issuedAt = issuedAt;
    }

    @Override
    public boolean updateData(LicenseSummary summary) {
        boolean res = false;
        if(!this.licenseName.equals(summary.getLicenseName())) {
            this.licenseName = summary.getLicenseName(); res = true;
        }
        if(!this.organizationName.equals(summary.getOrganizationName())) {
            this.organizationName = summary.getOrganizationName(); res = true;
        }
        if(!this.issuedAt.equals(summary.getIssuedAt())) {
            issuedAt = LocalDateTime.of(summary.getIssuedAt().toLocalDate(), summary.getIssuedAt().toLocalTime());
            res = true;
        }
        return res;
    }

    @Override
    public boolean isDifferentWith(Object o) {
        License other = (License) o;
        return !(this.licenseName.equals(other.getLicenseName())
                && this.organizationName.equals(other.getOrganizationName())
                && this.issuedAt.equals(other.getIssuedAt()));
    }

    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
        if(!(obj instanceof License)) return false;
        Updatable up = (Updatable)obj;
        return up.getId().equals(this.id);
    }

    @Override
    public void setParent(Object parent) {
        this.setResume((Resume)parent);
    }
}
