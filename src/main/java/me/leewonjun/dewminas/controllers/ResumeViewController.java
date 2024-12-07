package me.leewonjun.dewminas.controllers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.leewonjun.dewminas.domains.User;
import me.leewonjun.dewminas.domains.of_resume.Resume;
import me.leewonjun.dewminas.dto.client_dto.ResumeResponse;
import me.leewonjun.dewminas.dto.client_dto.ResumeSummary;
import me.leewonjun.dewminas.services.ResumeService;
import me.leewonjun.dewminas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Getter
@Setter
@RequiredArgsConstructor
public class ResumeViewController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final ResumeService resumeService;

    @GetMapping("/profile")
    public String getProfile(Model model, @RequestParam("email") String email) {
        User owner = userService.findUser(email);
        ResumeSummary resumeSummary = null;
        try{
            Resume resume = resumeService.findResume(email);
            resumeSummary = new ResumeSummary(resume.getId(), resume.getDesiredPosition(), resume.getCreatedAt(), resume.getUpdatedAt());
        } catch (IllegalArgumentException e) {
        }
        model.addAttribute("email", owner.getEmail());
        model.addAttribute("nameKor", owner.getNameKor());
        model.addAttribute("nameEng", owner.getNameEng());
        model.addAttribute("nickname", owner.getNickname());
        model.addAttribute("phoneNumber", owner.getPhoneNumber());
        model.addAttribute("resume", resumeSummary);
        return "profile";
    }

    @GetMapping("/resume-update")
    public String updateResume(Model model, @RequestParam("email") String email) {


        return "resume-update";
    }
}
