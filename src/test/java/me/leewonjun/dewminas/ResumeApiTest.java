package me.leewonjun.dewminas;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import me.leewonjun.dewminas.domains.*;
import me.leewonjun.dewminas.domains.of_resume.*;
import me.leewonjun.dewminas.dto.client_dto.RegisterResumeRequest;
import me.leewonjun.dewminas.dto.client_dto.UpdateResumeRequest;
import me.leewonjun.dewminas.dto.resume_sub.*;
import me.leewonjun.dewminas.repositories.*;
import me.leewonjun.dewminas.repositories.resume_repo.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
이력서 CURD 테스트용 클래스
 */


@ContextConfiguration(classes = {DewminasApplication.class})
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class ResumeApiTest {
    public final String src = "/api/resume";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private AcademicActivityRepository academicActivityRepository;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private EducationalExpRepository educationalExpRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private WorkExpRepository workExpRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private User newUser = null;

    @BeforeEach
    public void mockSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        academicActivityRepository.deleteAll();
        awardRepository.deleteAll();
        educationRepository.deleteAll();
        educationalExpRepository.deleteAll();
        licenseRepository.deleteAll();
        workExpRepository.deleteAll();
        resumeRepository.deleteAll();
        userRepository.deleteAll();
        userRepository.flush();
        newUser = userRepository.save(User.builder().nameKor("이원준").nameEng("Lee WonJun").phoneNumber("01012341111").email("mail@gmail.com").nickname("tester").password("1234").build());
    }

    @DisplayName("postResume() : 이력서 등록에 성공한다.")
    @Test
    @Transactional
    @Rollback
    public void postingTest() throws Exception{
        // given : 유저 데이터 저장, 이력서 정보 post

        String pnum = "01012341111";
        String url = src;
        // 전송할 이력서 기본 정보
        RegisterResumeRequest request = new RegisterResumeRequest(newUser.getEmail());

        // when
        ResultActions res = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        res.andExpect(status().isCreated());
        Resume newResume = resumeRepository.findByOwner(newUser).get();
        Assertions.assertThat(newResume).isNotNull();
        Assertions.assertThat(newResume.getOwner().getId()).isEqualTo(newUser.getId());
        Assertions.assertThat(newResume.getOwner().getPhoneNumber()).isEqualTo(pnum);
        Assertions.assertThat(newResume.getOwner().getNickname()).isEqualTo(newUser.getNickname());
        Assertions.assertThat(newResume.getOwner().getEmail()).isEqualTo(newUser.getEmail());
    }

    @DisplayName("findResume() : 이력서 조회에 성공한다.")
    @Test
    @Transactional
    @Rollback
    public void findingTest() throws Exception{
        // given
        String pnum = "01012341111";
        Resume newResume = resumeRepository.save(Resume.builder().owner(newUser).build());
        String url = src+"?email=mail@gmail.com";

        // when
        ResultActions actions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.resumeId").value(newResume.getId()));
        actions.andExpect(jsonPath("$.phoneNumber").value(newResume.getOwner().getPhoneNumber()));
        actions.andExpect(jsonPath("$.email").value(newUser.getEmail()));
    }

    @DisplayName("updateResume() #1 : 이력서 정보 추가에 성공한다.")
    @Test
    @Transactional
    @Rollback
    public void appendInformation() throws Exception {
        // given : 유저 생성, 이력서 생성, 추가할 정보 생성
        String url = src;

        String pnum = "01034422631";
        User owner = newUser;
        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());
        List<EducationSummary> eduSums = new ArrayList<>();
        List<LicenseSummary> licenseSums = new ArrayList<>();
        List<AwardSummary> awardSums = new ArrayList<>();
        List<AcademicActivitySummary> acaSums = new ArrayList<>();
        List<EducationalExpSummary> eduExpSums = new ArrayList<>();
        List<WorkExpSummary> workSums = new ArrayList<>();
        String desiredPos = "Back-end developer";
        {
            // 정보 입력 스코프
            eduSums.add(new EducationSummary(0, "동의대학교", "컴퓨터공학과", "학사", 4.49, 4.5,
                    LocalDateTime.of(2014, java.time.Month.of(3), 2, 0, 0, 0),
                    LocalDateTime.of(2024, java.time.Month.of(3), 14, 0, 0, 0), true, 4));
            licenseSums.add(new LicenseSummary("정보처리기사", "산업인력공단",
                    LocalDateTime.of(2024, java.time.Month.of(6), 11, 0, 0, 0)));
            awardSums.add(new AwardSummary("우수상", "캡스톤디자인 경진대회", "동의대 LINC+사업단",
                    LocalDateTime.of(2024, java.time.Month.of(8), 20, 0, 0, 0)));
            eduExpSums.add(new EducationalExpSummary("SSAFY", "고용노동부",
                    LocalDateTime.of(2025, java.time.Month.of(1), 1, 0, 0, 0)
                    ,LocalDateTime.of(2024, java.time.Month.of(12), 31, 0, 0, 0), true ));
            acaSums.add(new AcademicActivitySummary("횡단보도 사각 보조 시스템 논문발표", "한국정보기술학회", "추계종합학술대회",
                    LocalDateTime.of(2024, java.time.Month.of(11), 22, 13, 32, 0)));
            workSums.add(new WorkExpSummary(new WorkExp("네이버", "개발팀장",
                    LocalDateTime.of(2025,java.time.Month.of(3), 1, 0 ,0 ,0),
                    LocalDateTime.of(2025, java.time.Month.of(12), 3, 0, 0, 0), true, "서버개발")));
        }

        UpdateResumeRequest request = new UpdateResumeRequest(desiredPos, eduSums, licenseSums, awardSums, acaSums, eduExpSums, workSums);
        // when
        mockMvc.perform(put(url+"?id="+resume.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        //resumeRepository.flush();
        // fetch를 FetchType.EAGER로 변경하면 통과한다. 그런데 왜인지 모르겠다.
        // then
        Resume newResume = resumeRepository.findByOwner(owner).orElse(null);

        Assertions.assertThat(newResume).isNotNull();
        Assertions.assertThat(newResume.getOwner()).isEqualTo(owner);
        Assertions.assertThat(newResume.getDesiredPosition()).isEqualTo(desiredPos);

        //
//
//        Assertions.assertThat(newResume.getAwards().size()).isOne();
//        Assertions.assertThat(newResume.getAwards().get(0).isDifferentWith(awardSums.get(0).specify())).isFalse();
//
//        Assertions.assertThat(newResume.getAcademicActivities().size()).isOne();
//        Assertions.assertThat(newResume.getAcademicActivities().get(0).isDifferentWith(acaSums.get(0).specify())).isFalse();
//
//        Assertions.assertThat(newResume.getEducations().size()).isOne();
//        Assertions.assertThat(newResume.getEducations().get(0).isDifferentWith(eduSums.get(0).specify())).isFalse();
//
//        Assertions.assertThat(newResume.getEduExps().size()).isOne();
//        Assertions.assertThat(newResume.getEduExps().get(0).isDifferentWith(eduExpSums.get(0).specify())).isFalse();
//
//        Assertions.assertThat(newResume.getLicenses().size()).isOne();
//        Assertions.assertThat(newResume.getLicenses().get(0).isDifferentWith(licenseSums.get(0).specify())).isFalse();
//
//        Assertions.assertThat(newResume.getWorkExps().size()).isOne();
//        Assertions.assertThat(newResume.getWorkExps().get(0).isDifferentWith(workSums.get(0).specify())).isFalse();

        List<Education> educations = educationRepository.findByResume(resume);
        List<Award> awards = awardRepository.findByResume(resume);
        List<EducationalExp> educationalExps = educationalExpRepository.findByResume(resume);
        List<AcademicActivity> academicActivities = academicActivityRepository.findByResume(resume);
        List<License> licenses = licenseRepository.findByResume(resume);
        List<WorkExp> workExps = workExpRepository.findByResume(resume);

        Assertions.assertThat(educations.size()).isOne();
        Assertions.assertThat(educations.get(0).isDifferentWith(eduSums.get(0).specify())).isFalse();

        Assertions.assertThat(awards.size()).isOne();
        Assertions.assertThat(awards.get(0).isDifferentWith(awardSums.get(0).specify())).isFalse();

        Assertions.assertThat(educationalExps.size()).isOne();
        Assertions.assertThat(educationalExps.get(0).isDifferentWith(eduExpSums.get(0).specify())).isFalse();

        Assertions.assertThat(academicActivities.size()).isOne();
        Assertions.assertThat(academicActivities.get(0).isDifferentWith(acaSums.get(0).specify())).isFalse();

        Assertions.assertThat(licenses.size()).isOne();
        Assertions.assertThat(licenses.get(0).isDifferentWith(licenseSums.get(0).specify())).isFalse();

        Assertions.assertThat(workExps.size()).isOne();
        Assertions.assertThat(workExps.get(0).isDifferentWith(workSums.get(0).specify())).isFalse();

    }

    @DisplayName("updateResume() #2 : 이력서 정보 수정에 성공한다.")
    @Test
    @Transactional
    @Rollback
    public void updateInformation() throws Exception {
        // given : 유저 생성, 이력서 생성, 추가할 정보 생성
        String url = src;

        String pnum = "01034422631";
        User owner = newUser;
        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());
        List<EducationSummary> eduSums = new ArrayList<>();
        List<LicenseSummary> licenseSums = new ArrayList<>();
        List<AwardSummary> awardSums = new ArrayList<>();
        List<AcademicActivitySummary> acaSums = new ArrayList<>();
        List<EducationalExpSummary> eduExpSums = new ArrayList<>();
        List<WorkExpSummary> workSums = new ArrayList<>();
        String desiredPos = "Back-end developer";
        {
            // 정보 입력 스코프
            eduSums.add(new EducationSummary(0, "동의대학교", "컴퓨터공학과", "학사", 4.49, 4.5,
                    LocalDateTime.of(2014, java.time.Month.of(3), 2, 0, 0, 0),
                    LocalDateTime.of(2024, java.time.Month.of(3), 14, 0, 0, 0), true, 4));
            licenseSums.add(new LicenseSummary("정보처리기사", "산업인력공단",
                    LocalDateTime.of(2024, java.time.Month.of(6), 11, 0, 0, 0)));
            awardSums.add(new AwardSummary("우수상", "캡스톤디자인 경진대회", "동의대 LINC+사업단",
                    LocalDateTime.of(2024, java.time.Month.of(8), 20, 0, 0, 0)));
            eduExpSums.add(new EducationalExpSummary("SSAFY", "고용노동부",
                    LocalDateTime.of(2025, java.time.Month.of(1), 1, 0, 0, 0)
                    ,LocalDateTime.of(2024, java.time.Month.of(12), 31, 0, 0, 0), true ));
            acaSums.add(new AcademicActivitySummary("횡단보도 사각 보조 시스템 논문발표", "한국정보기술학회", "추계종합학술대회",
                    LocalDateTime.of(2024, java.time.Month.of(11), 22, 13, 32, 0)));
            workSums.add(new WorkExpSummary(new WorkExp("네이버", "개발팀장",
                    LocalDateTime.of(2025,java.time.Month.of(3), 1, 0 ,0 ,0),
                    LocalDateTime.of(2025, java.time.Month.of(12), 3, 0, 0, 0), true, "서버개발")));
        }

        UpdateResumeRequest request = new UpdateResumeRequest(desiredPos, eduSums, licenseSums, awardSums, acaSums, eduExpSums, workSums);
        // when
        mockMvc.perform(put(url+"?id="+resume.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        request.setLicenses(new ArrayList<>()); // license 엔티티는 삭제
        request.getWorkExps().add(new WorkExpSummary(new WorkExp("부산은행", "전자 결제 시스템 개발팀원",
                LocalDateTime.of(2023,java.time.Month.of(1), 1, 0, 0, 0),
                LocalDateTime.of(2024, java.time.Month.of(2), 1, 0, 0, 0),
                false, "QA")));
        request.getAwards().get(0).setAwardName("대상");
        request.getAwards().get(0).setOrganizationName("서울대학교");
        request.getAwards().get(0).setCompetitionName("전국 프로그래밍 경진대회");

        request.getAwards().get(0).setId(1L);
        request.getAcademicActivities().get(0).setId(1L);
        request.getEducations().get(0).setId(1L);
        request.getEduExps().get(0).setId(1L);
        request.getWorkExps().get(0).setId(1L);

        mockMvc.perform(put(url+"?id="+resume.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        // then 변화한 데이터 확인. license 엔티티 = 0, award 변화, workExp 변화
        Award updatedAward = (Award) request.getAwards().get(0).specify(), awardFromDB = awardRepository.findByResume(resume).get(0);
        WorkExp newWorkExp = (WorkExp) request.getWorkExps().get(1).specify(), workExpFromDB = workExpRepository.findByCompanyName("부산은행").get(0);

        Assertions.assertThat(licenseRepository.count()).isZero();
        Assertions.assertThat(updatedAward.isDifferentWith(awardFromDB)).isFalse();
        Assertions.assertThat(workExpRepository.count()).isEqualTo(2L);
        Assertions.assertThat(newWorkExp.isDifferentWith(workExpFromDB)).isFalse();
    }

    @DisplayName("deleteResume() : 이력서 및 세부 정보 삭제에 성공한다.")
    @Test
    @Transactional
    @Rollback
    public void deleteResumeTest()  throws Exception{
        // given : 유저 생성, 이력서 생성, 추가할 정보 생성
        String pnum = "01034422631";
        User owner = newUser;

        String url = src;

        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());
        List<EducationSummary> eduSums = new ArrayList<>();
        List<LicenseSummary> licenseSums = new ArrayList<>();
        List<AwardSummary> awardSums = new ArrayList<>();
        List<AcademicActivitySummary> acaSums = new ArrayList<>();
        List<EducationalExpSummary> eduExpSums = new ArrayList<>();
        List<WorkExpSummary> workSums = new ArrayList<>();
        String desiredPos = "Back-end developer";
        {
            // 정보 입력 스코프
            eduSums.add(new EducationSummary(0, "동의대학교", "컴퓨터공학과", "학사", 4.49, 4.5,
                    LocalDateTime.of(2014, java.time.Month.of(3), 2, 0, 0, 0),
                    LocalDateTime.of(2024, java.time.Month.of(3), 14, 0, 0, 0), true, 4));
            licenseSums.add(new LicenseSummary("정보처리기사", "산업인력공단",
                    LocalDateTime.of(2024, java.time.Month.of(6), 11, 0, 0, 0)));
            awardSums.add(new AwardSummary("우수상", "캡스톤디자인 경진대회", "동의대 LINC+사업단",
                    LocalDateTime.of(2024, java.time.Month.of(8), 20, 0, 0, 0)));
            eduExpSums.add(new EducationalExpSummary("SSAFY", "고용노동부",
                    LocalDateTime.of(2025, java.time.Month.of(1), 1, 0, 0, 0)
                    ,LocalDateTime.of(2024, java.time.Month.of(12), 31, 0, 0, 0), true ));
            acaSums.add(new AcademicActivitySummary("횡단보도 사각 보조 시스템 논문발표", "한국정보기술학회", "추계종합학술대회",
                    LocalDateTime.of(2024, java.time.Month.of(11), 22, 13, 32, 0)));
            workSums.add(new WorkExpSummary(new WorkExp("네이버", "개발팀장",
                    LocalDateTime.of(2025,java.time.Month.of(3), 1, 0 ,0 ,0),
                    LocalDateTime.of(2025, java.time.Month.of(12), 3, 0, 0, 0), true, "CTO")));
        }

        UpdateResumeRequest request = new UpdateResumeRequest(desiredPos, eduSums, licenseSums, awardSums, acaSums, eduExpSums, workSums);
        // when
        mockMvc.perform(put(url+"?id="+resume.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        mockMvc.perform(delete(url+"?email="+owner.getEmail())).andExpect(status().isOk());

        // then 모든 정보 삭제된 것을 검증한다.
        Assertions.assertThat(resumeRepository.count()).isZero();
        Assertions.assertThat(academicActivityRepository.count()).isZero();
        Assertions.assertThat(awardRepository.count()).isZero();
        Assertions.assertThat(educationRepository.count()).isZero();
        Assertions.assertThat(educationalExpRepository.count()).isZero();
        Assertions.assertThat(licenseRepository.count()).isZero();
        Assertions.assertThat(workExpRepository.count()).isZero();
        Assertions.assertThat(userRepository.findById(owner.getId()).orElse(null)).isNotNull();
    }

    @DisplayName("수정 성능 테스트 : brutal way")
    @Test
    @Transactional
    @Rollback
    public void loadTest() throws Exception {
        // 테스트 데이터 준비
        User owner = newUser;
        String url = src;
        Resume resume = resumeRepository.save(Resume.builder().owner(owner).build());

        // 각 엔티티별 데이터 삽입
        for(int i = 0; i < 1000; i++){
            // 정보 입력 스코프
            educationRepository.save(new Education(0, "동의대학교", "컴퓨터공학과", "학사", 4.49, 4, 4.5,
                    LocalDateTime.of(2014, java.time.Month.of(3), 2, 0, 0, 0),
                    LocalDateTime.of(2024, java.time.Month.of(3), 14, 0, 0, 0), true, resume));
            licenseRepository.save(new License("정보처리기사", "산업인력공단",
                    LocalDateTime.of(2024, java.time.Month.of(6), 11, 0, 0, 0), resume));
            awardRepository.save(new Award("우수상", "캡스톤디자인 경진대회", "동의대 LINC+사업단",
                    LocalDateTime.of(2024, java.time.Month.of(8), 20, 0, 0, 0), resume));
            educationalExpRepository.save(new EducationalExp("SSAFY", "고용노동부",
                    LocalDateTime.of(2025, java.time.Month.of(1), 1, 0, 0, 0)
                    ,LocalDateTime.of(2024, java.time.Month.of(12), 31, 0, 0, 0), true, resume));
            academicActivityRepository.save(new AcademicActivity("횡단보도 사각 보조 시스템 논문발표", "한국정보기술학회", "추계종합학술대회",
                    LocalDateTime.of(2024, java.time.Month.of(11), 22, 13, 32, 0), resume));
            workExpRepository.save(new WorkExp("네이버", "개발팀장",
                    LocalDateTime.of(2025,java.time.Month.of(3), 1, 0 ,0 ,0),
                    LocalDateTime.of(2025, java.time.Month.of(12), 3, 0, 0, 0), true, "CTO", resume));
        }

        // 타겟 테이터 로드
        List<EducationSummary> eduDummies = educationRepository.findAll().stream().map(EducationSummary::new).toList();
        List<EducationalExpSummary> eduExpDummies = educationalExpRepository.findAll().stream().map(EducationalExpSummary::new).toList();
        List<AwardSummary> awardDummies = awardRepository.findAll().stream().map(AwardSummary::new).toList();
        List<AcademicActivitySummary> acaExpDummies = academicActivityRepository.findAll().stream().map(AcademicActivitySummary::new).toList();
        List<LicenseSummary> licenseDummies = licenseRepository.findAll().stream().map(LicenseSummary::new).toList();
        List<WorkExpSummary> workExpDummies = workExpRepository.findAll().stream().map(WorkExpSummary::new).toList();

        // 테스트 시작 : 100만 회의 변경 서비스 호출
        long start = System.currentTimeMillis();
        StringBuilder originalTarget = new StringBuilder("test");

        for(int i = 0; i < 100; i++) {
//            long startT = System.currentTimeMillis();
            UpdateResumeRequest request = new UpdateResumeRequest();
            for(int k = 0; k < 100; k++) {
                StringBuilder repTarget = new StringBuilder(originalTarget);
                int idx = (int)(Math.random() * 1000);
                eduDummies.get(idx).setInstitutionName(repTarget.append(i).toString());
                eduExpDummies.get(idx).setEducationName(repTarget.append(i).toString());
                awardDummies.get(idx).setAwardName(repTarget.append(i).toString());
                acaExpDummies.get(idx).setInstitutionName(repTarget.append(i).toString());
                licenseDummies.get(idx).setLicenseName(repTarget.append(i).toString());
                workExpDummies.get(idx).setCompanyName(repTarget.append(i).toString());
            }

            request.setEducations(eduDummies); request.setEduExps(eduExpDummies);
            request.setAwards(awardDummies); request.setAcademicActivities(acaExpDummies);
            request.setLicenses(licenseDummies); request.setWorkExps(workExpDummies);

            mockMvc.perform(put(url+"?id="+resume.getId())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());
//            System.out.println(System.currentTimeMillis() - startT);

        }

        // 테스트 종료
        System.out.println("소요 시간 : " + (System.currentTimeMillis() - start));

        // 개선 전 : (save) 13504
        // 개선 전 : (saveAll) 14572, 100회 : 158729
        // 개선 후 : 2582 (single thread), 100회 : 17590
        // 개선 후 : 916 (multi thread), runtime error -> resume null ? 아마 락 때문인 것 같음..
    }
}
