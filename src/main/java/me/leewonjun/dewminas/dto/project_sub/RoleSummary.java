package me.leewonjun.dewminas.dto.project_sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.Role;
import me.leewonjun.dewminas.domains.Updatable;
import me.leewonjun.dewminas.dto.resume_sub.Specifiable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleSummary implements Specifiable {
    private Long id;
    private String roleTitle;
    private String roleComment;

    public RoleSummary(Role role) {
        this.id = role.getId();
        this.roleTitle = role.getRoleTitle();
        this.roleComment = role.getRoleComment();
    }


    // specify에 ID는 포함 X -> insert에 사용되기 때문, DTO의 ID는 Update 연산 용임.
    @Override
    public Updatable<? extends Specifiable> specify() {
        return Role.builder().roleTitle(this.roleTitle).roleComment(this.roleComment).build();
    }

}
