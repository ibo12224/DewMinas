//부모 클래스 가장 앞 이니셜 따서 버튼 이름들 지정
//button_ci=>container_introdce
const frame = document.querySelector(".form");

document.addEventListener('DOMContentLoaded', function() {
   addFieldSet('skill');
   addFieldSet('career');
   addFieldSet('experience');
   addFieldSet('academy');
   addFieldSet('rewards');
   addFieldSet('education');
   addFieldSet('lisence');
   window.addEventListener("beforeunload", beforeUnloadHandler);

   const savedImageUrl = localStorage.getItem('imageUrl');
     if (savedImageUrl) {
       const imageContainer = document.querySelector('.image-container');
       imageContainer.style.backgroundImage = `url(${savedImageUrl})`;
     }


  document.querySelectorAll('.toggle-button').forEach(button => {
      const sectionName = button.getAttribute('data-section');
      // 로컬 저장소에서 해당 섹션의 'hidden' 상태와 'activate' 상태를 확인하고 복원
      const isHidden = localStorage.getItem(`section-${sectionName}-hidden`) === 'true';
      const isActivated = localStorage.getItem(`button-${sectionName}-activate`) === 'true';

      // 초기 상태 설정: 섹션과 자식 요소들의 'hidden' 클래스를 설정
      const section = document.querySelector(`[name="${sectionName}"]`);
      if (section) {
          if (isHidden) {
              section.classList.add('hidden');
              Array.from(section.children).forEach(child => child.classList.add('hidden'));
          }
      }

      // 버튼의 'activate' 상태 복원
      if (isActivated) {
          button.classList.add("activate");
      }

      button.addEventListener('click', () => {
          // 버튼 클릭 시 'activate' 클래스를 토글
          const isNowActivated = button.classList.toggle("activate");

          // 버튼의 'activate' 상태를 로컬 저장소에 저장
          localStorage.setItem(`button-${sectionName}-activate`, isNowActivated);

          // 버튼 클릭 시 해당 섹션을 찾고 'hidden' 클래스를 토글
          if (section) {
              const isNowHidden = section.classList.toggle('hidden');

              // 자식 요소들에 'hidden' 클래스도 토글
              Array.from(section.children).forEach(child => {
                  child.classList.toggle('hidden', isNowHidden);
              });

              // plusbutton도 섹션 내 자식 요소로 취급하여 'hidden' 클래스를 토글
              const plusButton = section.querySelector('.plusbutton');
              if (plusButton) {
                  plusButton.classList.toggle('hidden', isNowHidden);
              }

              // 섹션의 'hidden' 상태를 로컬 저장소에 저장
              localStorage.setItem(`section-${sectionName}-hidden`, isNowHidden);
          }
      });
  });
});



///////////////////////////////////////////////plusbutton
document.addEventListener('DOMContentLoaded', function () {
  // Initialize each section by loading saved field count from localStorage
  document.querySelectorAll('.plusbutton').forEach(button => {
      const section = button.getAttribute('data-section');
      const savedFieldCount = parseInt(localStorage.getItem(`fieldCount-${section}`)) || 0;

      // Load the saved field sets when the page loads
      for (let i = 0; i < savedFieldCount; i++) {
          addFieldSet(section);
      }
  });
});

document.querySelectorAll('.plusbutton').forEach(button => {
  button.addEventListener('click', (e) => {
      const section = e.currentTarget.getAttribute('data-section');
      addFieldSet(section);  // Add a new field set when the button is clicked

      // Save the updated field count to localStorage
      const fieldCountKey = `fieldCount-${section}`;
      const currentCount = parseInt(localStorage.getItem(fieldCountKey)) || 0;
      localStorage.setItem(fieldCountKey, currentCount + 1);
  });
});
function addFieldSet(section) {
  let template, container;
  let fieldPrefix; // Field prefix to keep track of the numbers for each section

  switch (section) {
      case 'skill':
          template = document.querySelector('.maincontainer[name="skill"] .subframe');
          container = document.querySelector('.maincontainer[name="skill"] .mainframe');
          fieldPrefix = 'skill'; // For skill section, use 'skill' as prefix
          break;
      case 'career':
          template = document.querySelector('.maincontainer[name="career"] .subframe');
          container = document.querySelector('.maincontainer[name="career"] .mainframe');
          fieldPrefix = 'career'; // For career section, use 'career' as prefix
          break;
      case 'experience':
          template = document.querySelector('.maincontainer[name="experience"] .subframe');
          container = document.querySelector('.maincontainer[name="experience"] .mainframe');
          fieldPrefix = 'experience';
          break;
      case 'academy':
          template = document.querySelector('.maincontainer[name="academy"] .subframe');
          container = document.querySelector('.maincontainer[name="academy"] .mainframe');
          fieldPrefix = 'academy';
          break;
      case 'rewards':
          template = document.querySelector('.maincontainer[name="rewards"] .subframe');
          container = document.querySelector('.maincontainer[name="rewards"] .mainframe');
          fieldPrefix = 'rewards';
          break;
      case 'education':
          template = document.querySelector('.maincontainer[name="education"] .subframe');
          container = document.querySelector('.maincontainer[name="education"] .mainframe');
          fieldPrefix = 'education';
          break;
      case 'lisence':
          template = document.querySelector('.maincontainer[name="lisence"] .subframe');
          container = document.querySelector('.maincontainer[name="lisence"] .mainframe');
          fieldPrefix = 'lisence';
          break;
      default:
          console.warn(`Unknown section: ${section}`);
          return;
  }

  // Clone the template and reset the input fields
  const newFieldSet = template.cloneNode(true);
  newFieldSet.querySelectorAll('input, textarea,select, check,e_check').forEach(input => input.value = '');
  newFieldSet.style.display = 'flex';

  // Add numbering to the field sets for each section
  const allFieldSets = container.querySelectorAll('.subframe');
  const index = allFieldSets.length-1; // Determine the new fieldset's number

  newFieldSet.querySelectorAll('input, textarea,select,check,e_check').forEach(input => {
      const name = input.name.replace(/\[\d+\]/, `[${index}]`); // Update the field name with the new index
      input.name = name;
  });

  // Add the new field set to the container
  container.appendChild(newFieldSet);
}



//이미지 추가하는 함수임
document.getElementById('addImageBtn').addEventListener('click', function() {
  document.getElementById('imageInput').click();
});

document.getElementById('imageInput').addEventListener('change', function(event) {
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

document.addEventListener('DOMContentLoaded', function () {
  const previewButton = document.querySelector('#preview'); // 미리보기 버튼

  // 미리보기 버튼 클릭 이벤트
  previewButton.addEventListener('click', function () {
    const allData = {
      educations: [],
      awards: [],
      eduExps: [],
      academicActivities: [],
      workExps: [],
      licenses: [],
      desiredPosition: "" // desiredPosition 추가
    };

    // 모든 입력 값 수집 (hidden 클래스 제외)
    document.querySelectorAll('.text_area, .text_area_1, .select, .check, .e_check, .image').forEach(input => {
      if (!input.closest('.hidden')) { // hidden 클래스에 포함되지 않은 요소만 처리
        const name = input.getAttribute('name');
        if (!name) return; // name 속성이 없는 경우 무시

        console.log('Processing input:', name); // 디버깅용

        // desiredPosition 예외 처리
        if (name === "desiredPosition") {
          allData.desiredPosition = input.value.trim(); // desiredPosition은 배열이 아닌 단일 값으로 처리
          return; // 다른 처리 하지 않도록 early return
        }

        const matches = name.match(/^(\w+)\[(\d+)]\.(\w+)$/); // 정규식으로 파싱
        if (!matches) {
          console.warn(`Invalid name format: ${name}`);
          return;
        }

        const [, listName, index, field] = matches; // 정규식 그룹 매칭 결과
        const listIndex = parseInt(index, 10);

        if (!allData[listName]) {
          console.warn(`Undefined list name: ${listName}`);
          return;
        }

        // 배열 초기화
        if (!allData[listName][listIndex]) {
          allData[listName][listIndex] = {};
        }

        // 값을 추가하기 전에 유효한 값인지 체크
        if (input.type === 'checkbox') {
          // checkbox일 경우 체크된 경우에만 값을 추가
          if (input.checked) {
            allData[listName][listIndex][field] = "1";
          }
        } else if (input.value.trim() !== "") {
          // checkbox가 아닌 경우 빈 값이 아닌 것만 추가
          allData[listName][listIndex][field] = input.value.trim();
        }
      }
    });

    // 빈 객체를 가진 배열 항목을 제거하는 코드 추가
    Object.keys(allData).forEach(key => {
      if (key !== "desiredPosition") {  // desiredPosition은 배열이 아니므로 제외
        allData[key] = allData[key].filter(item => {
          return Object.keys(item).length > 0; // 객체가 비어 있지 않은 경우만 포함
        });
      }
    });

    // 데이터를 JSON 문자열로 변환하여 localStorage에 저장
    localStorage.setItem('formData', JSON.stringify(allData));
    window.removeEventListener("beforeunload", beforeUnloadHandler);
    console.log('Final JSON Data:', allData); // 최종 데이터 디버깅용 출력
  });
});




// beforeunload 이벤트 핸들러
function beforeUnloadHandler(event) {
  // 페이지가 닫히거나 다른 URL로 이동하기 직전에 실행될 코드
  console.log("페이지 정보를 저장하셨습니까?");
  event.preventDefault();
  event.returnValue = ""; // 경고 메시지 표시 (일부 브라우저만 지원)
  localStorage.clear(); // 페이지 닫힐 때 로컬 스토리지 초기화
}


document.addEventListener('click', function(event) {
  const wrapper = event.target.closest('.image-wrapper');
  const container = wrapper.closest('.maincontainer');
  const section = container.getAttribute('name');

  if (wrapper && wrapper.parentElement) {
    const fieldCountKey = `fieldCount-${section}`;
    const currentCount = parseInt(localStorage.getItem(fieldCountKey)) || 0;
    localStorage.setItem(fieldCountKey, currentCount - 1);
    wrapper.parentElement.remove();
  }
});