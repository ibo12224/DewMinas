package me.leewonjun.dewminas.dto.resume_sub;

import me.leewonjun.dewminas.domains.Updatable;

public interface Specifiable {
    // Update 연산에서 사용됨. 복합키 엔티티는 연산의 단순성을 위해 대조, 수정이 아닌 전체 삭제, 삽입으로 구현.
    // specify는 새로운 데이터 입력시 저장용 엔티티 객체 생성을 위해 호출됨.
    // 단 해당 엔티티가 Updatable을 구현해야 하기 때문에 복합키 엔티티에는 적용 불가능.
    public abstract Updatable<? extends Specifiable> specify();
    public Long getId();
}
