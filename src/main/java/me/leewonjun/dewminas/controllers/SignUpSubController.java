package me.leewonjun.dewminas.controllers;

import lombok.RequiredArgsConstructor;
import me.leewonjun.dewminas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpSubController {
    @Autowired
    private final UserService userService;

    @PostMapping("/api/dupcheck")
    public ResponseEntity<DupCheckRes> checkDuplicates(@RequestBody DupCheckReq request) {
        boolean res = userService.validateUniqueness(request.email());
        return ResponseEntity.ok().body(new DupCheckRes((res ? 1 : 0)));
    }
    record DupCheckRes(int checkRes) {};
    record DupCheckReq(String email) {};
}
