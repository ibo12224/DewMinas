package me.leewonjun.dewminas.controllers;

import lombok.RequiredArgsConstructor;
import me.leewonjun.dewminas.domains.Project;
import me.leewonjun.dewminas.dto.client_dto.ProjectResponse;
import me.leewonjun.dewminas.dto.client_dto.RegisterProjectRequest;
import me.leewonjun.dewminas.dto.client_dto.UpdateProjectRequest;
import me.leewonjun.dewminas.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProjectApiController {

    @Autowired
    private final ProjectService projectService;

    @GetMapping("/api/project")
    public ResponseEntity<ProjectResponse> findProject(
            @RequestParam(name = "project_id", required = true) Long projectId)
    {
        Project p = projectService.findProject(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(new ProjectResponse(p));
    }

    @PostMapping("/api/project")
    public ResponseEntity<Object> registerProject(
            @RequestBody RegisterProjectRequest request
    ) {
        projectService.registerProject(request.getOwnerEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/api/project")
    public ResponseEntity<ProjectResponse> updateProject (
            @RequestParam(name = "project_id", required = true) Long projectId,
            @RequestBody UpdateProjectRequest request
    ){
        projectService.updateProject(projectId, request);
        // 프로젝트 엔티티 검색 -> 반환
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/project")
    public ResponseEntity<Object> deleteProject(
            @RequestParam(name = "project_id", required = true) Long projectId)
    {

        return ResponseEntity.ok().build();
    }
}
