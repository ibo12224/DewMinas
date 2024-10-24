package me.leewonjun.dewminas.controllers;

import lombok.RequiredArgsConstructor;
import me.leewonjun.dewminas.dto.RegisterResumeRequest;
import me.leewonjun.dewminas.dto.ResumeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ResumeApiController {

    @GetMapping("/api/resume/{email}")
    public ResponseEntity findResume(@PathVariable(name = "email") String email) {
        ResumeResponse response;

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/api/resume")
    public ResponseEntity postResume(@RequestBody RegisterResumeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/api/resume")
    public ResponseEntity updateResume(@RequestBody RegisterResumeRequest request) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/api/resume/{email}")
    public ResponseEntity deleteResume(@PathVariable(name = "email") String email) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
