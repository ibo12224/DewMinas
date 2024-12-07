document.addEventListener('DOMContentLoaded', function() {
window.addEventListener("beforeunload", beforeUnloadHandler);

});


document.getElementById("preview-button").addEventListener("click", () => {
    // Form 데이터를 수집
    const formData = new FormData(document.getElementById("projectForm"));
    
    // 데이터 가공
    const result = {
      project: {
        name: formData.get("projectName")?.trim() || null,
        description: formData.get("projectDescription")?.trim() || null,
        link: formData.get("projectLink")?.trim() || null,
        summary: formData.get("projectSummary")?.trim() || null
      },
      roles: [
        {
          role: formData.get("role")?.trim() || null,
          description: formData.get("roleDescription")?.trim() || null
        }
      ],
      technologies: [],
      problemSolving: []
    };
  
    // 기술 데이터 처리: 빈 문자열은 제외
    const techInput = formData.get("usedTech")?.trim();
    if (techInput && techInput !== "") {
      result.technologies = techInput.split(",").map(tech => tech.trim());
    }
  
    // 문제 해결 데이터 처리: 빈 문자열은 제외
    const problem = formData.get("problem")?.trim();
    const solution = formData.get("solution")?.trim();
    if (problem && solution) {
      result.problemSolving.push({ problem, solution });
    }
  
    // 결과 확인 (콘솔 출력)
    console.log("가공된 데이터:", result);
  });
  