const profile = JSON.parse(localStorage.getItem('formData'));  // 저장된 데이터 가져오기
const project = JSON.parse(localStorage.getItem('formData'));  // 저장된 데이터 가져오기
const portfolioData = JSON.parse(`[
    {
        "id": 1,
        "desiredPosition": "Back-end developer",
        "createdAt": "2024-01-01",
        "updatedAt": "2024-01-02"
    }
]`);




document.addEventListener("DOMContentLoaded", function () {
    const editButton = document.getElementById("edit");
    const saveButton = document.getElementById("save");
    const inputs = document.querySelectorAll(".profile-input");

    // 수정 버튼 클릭 이벤트
    editButton.addEventListener("click", function () {
        inputs.forEach(input => input.disabled = false); // 모든 input 활성화
        editButton.style.display = "none"; // 수정 버튼 숨김
        saveButton.style.display = "inline"; // 저장 버튼 표시
    });

    // 저장 버튼 클릭 이벤트
    saveButton.addEventListener("click", function () {
        const formData = {};

        inputs.forEach(input => {
            formData[input.name] = input.value; // input 데이터를 formData 객체에 저장
            input.disabled = true; // 모든 input 비활성화
        });

        editButton.style.display = "inline"; // 수정 버튼 표시
        saveButton.style.display = "none"; // 저장 버튼 숨김

        console.log("저장된 데이터:", JSON.stringify(formData)); // JSON 형태로 출력
        // 필요한 경우 데이터를 서버로 전송하는 코드를 추가
        // 예: fetch 또는 axios 사용
    });
});


// 포트폴리오 요소를 채워주는 함수
function renderPortfolio(data) {
    const portfolioContainer = document.getElementById('portfolio-container'); // 이력서 정보를 표시할 컨테이너
    const plusButton = document.querySelector('.plus-button'); // + 버튼 가져오기

    // 데이터가 존재할 경우
    if (data) {
        plusButton.style.display = 'none'; // 데이터가 있을 경우, plus-button 숨기기

        portfolioContainer.innerHTML = `
            <label class="portfolio-text-1" name="desiredPosition">${data.desiredPosition}</label>
            <label class="portfolio-text-2">${data.createdAt}</label>
            <label class="portfolio-text-2">${data.updatedAt}</label>
            <button class="fix-button" id="f-fix-button-${data.id}" onclick="editPortfolio(${data.id})">수정</button>
            <button class="delete-button" id="f-delete-button-${data.id}" onclick="deletePortfolio(${data.id})">삭제</button>
        `;
    } else {
        
        portfolioContainer.innerHTML = ''; // 기존 표시된 이력서 정보 제거
        plusButton.style.display = 'flex'; // 데이터가 없으면, plus-button 보이기
    }
}

// 수정 버튼 클릭 시 호출되는 함수
function editPortfolio(id) {
    console.log(`Edit portfolio with id: ${id}`);
    // 수정 로직 추가
}

// 삭제 버튼 클릭 시 호출되는 함수
function deletePortfolio(id) {
    console.log(`Delete portfolio with id: ${id}`);
    // 삭제 로직 추가
}


window.onload = function () {


    if (portfolioData && portfolioData.length > 0) {
        // 데이터가 존재하면 첫 번째 요소를 renderPortfolio로 전달
        renderPortfolio(portfolioData[0]);
    } else {
        // 데이터가 없으면 null 전달
        renderPortfolio(null);
    }
};
