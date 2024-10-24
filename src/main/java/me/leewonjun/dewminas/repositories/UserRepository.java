package me.leewonjun.dewminas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.leewonjun.dewminas.domains.User;

// 회원가입, 회원탈퇴 처리용 리포지토리
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
