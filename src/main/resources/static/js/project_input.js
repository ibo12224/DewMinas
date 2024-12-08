document.addEventListener('DOMContentLoaded', function() {
window.addEventListener("beforeunload", beforeUnloadHandler);
  addFieldSet('problemSection');
});

//이미지 추가하는 함수임
document.getElementById('image-button').addEventListener('click', function() {
  document.getElementById('image-input').click();
});
document.getElementById('image-input').addEventListener('change', function(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function(e) {
      const imageContainer = document.querySelector('.image-container');
      const imageUrl = e.target.result;
      localStorage.setItem('imageUrl', imageUrl);
      imageContainer.style.backgroundImage = `url(${e.target.result})`;  // 배경 이미지로 설정
    };
    reader.readAsDataURL(file);
  }
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
    document.querySelectorAll("[name='usedTech']").forEach(input => {
      const tech = input.value.trim();
      if (tech) {
          result.technologies.push(...tech.split(",").map(item => item.trim()));
      }
    });
  
    const problemSections = document.querySelectorAll(".subframe");
    problemSections.forEach(section => {
        const problem = section.querySelector("[name='problem']")?.value.trim() || null;
        const solution = section.querySelector("[name='solution']")?.value.trim() || null;
        const resultText = section.querySelector("[name='result']")?.value.trim() || null;
        const lesson = section.querySelector("[name='lesson']")?.value.trim() || null;

        // 문제 설명, 해결 방법, 결과, 배운 점 중 하나라도 비어있지 않으면 추가
        if (problem || solution || resultText || lesson) {
            result.problemSolving.push({
                problem,
                solution,
                result: resultText,
                lesson
            });
        }    
      });
  
    // 결과 확인 (콘솔 출력)
    console.log("가공된 데이터:", result);
  });
  