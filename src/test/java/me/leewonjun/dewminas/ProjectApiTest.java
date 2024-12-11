//package me.leewonjun.dewminas;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import me.leewonjun.dewminas.controllers.IUserInfoExtractor;
//import me.leewonjun.dewminas.domains.Project;
//import me.leewonjun.dewminas.domains.User;
//import me.leewonjun.dewminas.domains.of_resume.Resume;
//import me.leewonjun.dewminas.dto.client_dto.RegisterProjectRequest;
//import me.leewonjun.dewminas.dto.client_dto.UpdateProjectRequest;
//import me.leewonjun.dewminas.dto.project_sub.*;
//import me.leewonjun.dewminas.repositories.UserRepository;
//import me.leewonjun.dewminas.repositories.project_repo.*;
//import me.leewonjun.dewminas.repositories.resume_repo.ResumeRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ProjectApiTest {
//    @Autowired
//    protected WebApplicationContext context;
//    @Autowired
//    protected MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private SkillRepository skillRepository;
//
//    @Autowired
//    private TroubleshootingRepository troubleshootingRepository;
//
//    @Autowired
//    private ProjectSourceRepository projectSourceRepository;
//
//    @Autowired
//    private RepositoryLinkRepository repositoryLinkRepository;
//
//    @Autowired
//    private ProjectPhotoRepository projectPhotoRepository;
//
//    @Autowired
//    private ResumeRepository resumeRepository;
//
//    @Autowired
//    private SkillBookRepository skillBookRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    public void setContext() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//        roleRepository.deleteAll();
//        skillRepository.deleteAll();
//        troubleshootingRepository.deleteAll();
//        projectSourceRepository.deleteAll();
//        repositoryLinkRepository.deleteAll();
//        projectPhotoRepository.deleteAll();
//        projectRepository.deleteAll();
//        resumeRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
//    @DisplayName("registerProject() : 프로젝트 등록에 성공한다.")
//    @Test
//    public void registerProjectTest() throws Exception {
//        // given : 유저 정보 삽입, 이력서 정보 삽입
//        User owner = userRepository.save(User.builder().nameKor("테스터").nameEng("tester").email("mail@gmail.com")
//                .nickname("test").password("1234").phoneNumber("010-1234-1234").build());
//        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());
//        RegisterProjectRequest request = new RegisterProjectRequest(owner.getEmail());
//
//        // when : POST : /api/project로 요청 전송 후 프로젝트 생성
//        ResultActions actions = mockMvc.perform(post("/api/project")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(request))
//        );
//
//        // then : 검증
//        actions.andExpect(status().isCreated());
//        Assertions.assertThat(projectRepository.count()).isOne();
//    }
//
//    @DisplayName("findProject() : 프로젝트 조회에 성공한다.")
//    @Test
//    public void findProjectTest() throws Exception{
//        // given : 유저 정보 삽입, 이력서 정보 삽입
//        User owner = userRepository.save(User.builder().nameKor("테스터").nameEng("tester").email("mail@gmail.com")
//                .nickname("test").password("1234").phoneNumber("010-1234-1234").build());
//        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());
//        Project project = projectRepository.save(Project.builder().resume(resume).ownerMail(owner.getEmail()).summary("test summary")
//                .title("백엔드 개발자가 될 거야").shortComment("테스트 프로젝트 인스턴스").build());
//
//        // when : API로 불러오기
//        ResultActions actions = mockMvc.perform(get("/api/project")
//                .param("id", project.getId().toString())
//                .accept(MediaType.APPLICATION_JSON)
//        );
//
//        // then : 검증
//        actions.andExpect(
//                jsonPath("$.id").value(project.getId())
//        );
//        actions.andExpect(
//                jsonPath("$.title").value(project.getTitle())
//        );
//        actions.andExpect(
//                jsonPath("$.summary").value(project.getSummary())
//        );
//        actions.andExpect(
//                jsonPath("$.email").value(project.getOwner())
//        );
//    }
//
//    @DisplayName("updateProject() : 프로젝트 정보 등록에 성공한다.")
//    @Test
//    public void updateProjectTest1() throws Exception {
//        // given : 유저 생성, 프로젝트 생성, 요청 객체 생성
//        User owner = userRepository.save(User.builder().nameKor("테스터").nameEng("tester").email("mail@gmail.com")
//                .nickname("test").password("1234").phoneNumber("010-1234-1234").build());
//        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());
//        Project project = projectRepository.save(Project.builder().resume(resume).ownerMail(owner.getEmail()).build());
//
//        String title = "백엔드 개발자가 될 거야", shortComment = "테스트 프로젝트 인스턴스", summary = "test summary";
//        UpdateProjectRequest request = new UpdateProjectRequest();
//        request.setId(project.getId());
//        request.setTitle(title);
//        request.setSummary(summary);
//        request.setShortComment(shortComment);
//        request.setOwner(owner.getEmail());
//
//        request.setSkills(new ArrayList<>());
//        request.getSkills().add(new SkillSummary(1, "python", null));
//
//        request.setRoles(new ArrayList<>());
//        request.getRoles().add(new RoleSummary(null, "팀장", "프로젝트 관리 및 서버 개발"));
//
//        request.setTroubleshooting(new ArrayList<>());
//        request.getTroubleshooting().add(new TroubleshootingSummary(null, "무결성 제약 조건 위배", "복합키는 수정 트랜잭션 제외", "위배 상황 발생 안함", "DB설계 시 자주 바뀌는 사항은 기본키에 포함하지 말 것"));
//
//        request.setProjectSources(new ArrayList<>());
//        request.getProjectSources().add(new ProjectSourceSummary("Spring Security"));
//
//        request.setRepositoryLinks(new ArrayList<>());
//        request.getRepositoryLinks().add(new RepositoryLinkSummary(1, "localhost:8080"));
//
//        // when : PUT : /api/project?id=1로 요청 전송
//        mockMvc.perform(put("/api/project").param("id", project.getId().toString())
//                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request))
//        );
//
//        // then 추가된 정보를 확인한다.
//        // 당장은 추가된 정보가 있는지만 확인하자.
//        Assertions.assertThat(projectRepository.findById(project.getId()).get().getShortComment()).isEqualTo(shortComment);
//        Assertions.assertThat(projectRepository.findById(project.getId()).get().getSummary()).isEqualTo(summary);
//        Assertions.assertThat(projectRepository.findById(project.getId()).get().getTitle()).isEqualTo(title);
//
//        Assertions.assertThat(roleRepository.count()).isOne();
//        Assertions.assertThat(troubleshootingRepository.count()).isOne();
//        Assertions.assertThat(projectSourceRepository.count()).isOne();
//        Assertions.assertThat(repositoryLinkRepository.count()).isOne();
//        Assertions.assertThat(skillRepository.count()).isOne();
//    }
//
//    @DisplayName("updateProject() : 프로젝트 정보 수정에 성공한다.")
//    @Test
//    public void updateProejctTest2() throws Exception{
//        // given : 유저 생성, 프로젝트 생성, 요청 객체 생성
//        User owner = userRepository.save(User.builder().nameKor("테스터").nameEng("tester").email("mail@gmail.com")
//                .nickname("test").password("1234").phoneNumber("010-1234-1234").build());
//        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());
//        Project project = projectRepository.save(Project.builder().resume(resume).ownerMail(owner.getEmail()).build());
//
//        String title = "백엔드 개발자가 될 거야", shortComment = "테스트 프로젝트 인스턴스", summary = "test summary";
//        UpdateProjectRequest request = new UpdateProjectRequest();
//        request.setId(project.getId());
//        request.setTitle(title);
//        request.setSummary(summary);
//        request.setShortComment(shortComment);
//        request.setOwner(owner.getEmail());
//
//        request.setSkills(new ArrayList<>());
//        request.getSkills().add(new SkillSummary(1, "python", null));
//
//        request.setRoles(new ArrayList<>());
//        request.getRoles().add(new RoleSummary(null, "팀장", "프로젝트 관리 및 서버 개발"));
//
//        request.setTroubleshooting(new ArrayList<>());
//        request.getTroubleshooting().add(new TroubleshootingSummary(null, "무결성 제약 조건 위배", "복합키는 수정 트랜잭션 제외", "위배 상황 발생 안함", "DB설계 시 자주 바뀌는 사항은 기본키에 포함하지 말 것"));
//
//        request.setProjectSources(new ArrayList<>());
//        request.getProjectSources().add(new ProjectSourceSummary("Spring Security"));
//
//        request.setRepositoryLinks(new ArrayList<>());
//        request.getRepositoryLinks().add(new RepositoryLinkSummary(1, "localhost:8080"));
//
//        mockMvc.perform(put("/api/project").param("id", project.getId().toString())
//                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request))
//        );
//
//        // 변경될 데이터 항목 :
//        // 1. title, summary, shortComment 변경 반영 확인
//        String newTitle = "새 제목";
//        String newSummary = "새 요약";
//        String newComment = "새 코멘트";
//        request.setTitle(newTitle);
//        request.setSummary(newSummary);
//        request.setShortComment(newComment);
//        // 2. role, troubleshooting 변경 반영, 추가, 삭제 확인
//        String newRoleComment = "새 롤 코멘트";
//        Long changedRoleId = roleRepository.findByProject(project).get(0).getId();
//        request.getRoles().get(0).setId(changedRoleId);
//        request.getRoles().get(0).setRoleComment(newRoleComment);
//        request.getRoles().add(new RoleSummary(null, "추가 롤 타이틀", "추가 롤 코멘트"));
//        request.getTroubleshooting().clear();
//        // 3. skill, projectSource, RepositoryLink 변경 반영,추가, 삭제 확인
//        request.getSkills().clear();
//        String newSourceName = "새 소스 이름";
//        request.getProjectSources().get(0).setSourceName(newSourceName);
//
//        // when : PUT : /api/project?id=1로 요청 전송
//        mockMvc.perform(put("/api/project").param("id", project.getId().toString())
//                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request))
//        );
//
//        // then 변경사항 검증
//        // #1 title, summary, shortComment
//        Assertions.assertThat(projectRepository.findById(project.getId())
//                .get().getTitle()
//        ).isEqualTo(newTitle);
//
//        Assertions.assertThat(projectRepository.findById(project.getId())
//                .get().getSummary()
//        ).isEqualTo(newSummary);
//
//        Assertions.assertThat(projectRepository.findById(project.getId())
//                .get().getShortComment()
//        ).isEqualTo(newComment);
//
//        // #2 role, troubleshooting 변경 확인
//        Assertions.assertThat(roleRepository.count()).isEqualTo(2);
//        Assertions.assertThat(roleRepository.findById(changedRoleId).get().getRoleComment()).isEqualTo(newRoleComment);
//        Assertions.assertThat(troubleshootingRepository.count()).isZero();
//
//        // #3 skill, projec Source 변경 확인
//        Assertions.assertThat(skillRepository.count()).isZero();
//        Assertions.assertThat(projectSourceRepository.findByProject(project).get().getId().getSourceName()).isEqualTo(newSourceName);
//
//    }
//
//    @DisplayName("deleteProject() : 프로젝트 삭제에 성공한다.")
//    @Test
//    public void deleteProjectTest() throws Exception {
//        // given : 유저 생성, 프로젝트 생성, 요청 객체 생성
//        User owner = userRepository.save(User.builder().nameKor("테스터").nameEng("tester").email("mail@gmail.com")
//                .nickname("test").password("1234").phoneNumber("010-1234-1234").build());
//        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());
//        Project project = projectRepository.save(Project.builder().resume(resume).ownerMail(owner.getEmail()).build());
//
//        String title = "백엔드 개발자가 될 거야", shortComment = "테스트 프로젝트 인스턴스", summary = "test summary";
//        UpdateProjectRequest request = new UpdateProjectRequest();
//        request.setId(project.getId());
//        request.setTitle(title);
//        request.setSummary(summary);
//        request.setShortComment(shortComment);
//        request.setOwner(owner.getEmail());
//
//        request.setSkills(new ArrayList<>());
//        request.getSkills().add(new SkillSummary(1, "python", null));
//
//        request.setRoles(new ArrayList<>());
//        request.getRoles().add(new RoleSummary(null, "팀장", "프로젝트 관리 및 서버 개발"));
//
//        request.setTroubleshooting(new ArrayList<>());
//        request.getTroubleshooting().add(new TroubleshootingSummary(null, "무결성 제약 조건 위배", "복합키는 수정 트랜잭션 제외", "위배 상황 발생 안함", "DB설계 시 자주 바뀌는 사항은 기본키에 포함하지 말 것"));
//
//        request.setProjectSources(new ArrayList<>());
//        request.getProjectSources().add(new ProjectSourceSummary("Spring Security"));
//
//        request.setRepositoryLinks(new ArrayList<>());
//        request.getRepositoryLinks().add(new RepositoryLinkSummary(1, "localhost:8080"));
//
//        // 정보 추가 요청 전송
//        mockMvc.perform(put("/api/project").param("id", project.getId().toString())
//                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request))
//        );
//
//        // when : 삭제 요청 전송
//        mockMvc.perform(delete("/api/project").param("id", project.getId().toString())).andExpect(status().isOk());
//
//        // 삭제 정보를 확인한다.
//        Assertions.assertThat(roleRepository.count()).isZero();
//        Assertions.assertThat(troubleshootingRepository.count()).isZero();
//        Assertions.assertThat(projectSourceRepository.count()).isZero();
//        Assertions.assertThat(repositoryLinkRepository.count()).isZero();
//        Assertions.assertThat(skillRepository.count()).isZero();
//
//        Assertions.assertThat(projectRepository.count()).isZero();
//    }
//}
