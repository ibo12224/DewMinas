//package me.leewonjun.dewminas.controllers;
//
//import me.leewonjun.dewminas.domains.User;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//// SecurityContext에서 유저 정보를 찾아 반환하는 인터페이스
//public interface IUserInfoExtractor {
//    default String getUsernameBySecurityContext() {
//        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//    }
//
//    default User getUserBySecurityContext() {
//        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//    }
//
//    default long getResumeIdBySecurityContext() {
//        return 0l;
//    }
//}
