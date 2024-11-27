// 프로필 데이터 및 포트폴리오 데이터 (서버 데이터는 비동기 호출)
const profileData = {
    nameKor: "홍길동",
    nameEng: "Hong Gildong",
    nickname: "길동",
    email: "gildong@example.com",
    phoneNumber: "010-1234-5678"
};

// 로컬 JSON 포트폴리오 데이터 예제
const portfolioData = [
    {
        id: 1,
        desiredPosition: "Back-end developer",
        createdAt: "2024-01-01",
        updatedAt: "2024-01-02"
    }
];

// 프로젝트 데이터 로컬 스토리지에서 가져오기
const project = JSON.parse(localStorage.getItem('formData')); 



// DOM 로드 후 실행
document.addEventListener("DOMContentLoaded", () => {
    setupProfileInputs();
    setupEditAndSaveButtons();
});
// 페이지 로드 시 호출되는 함수
window.onload = async () => {
    try {
        const portfolioData = await fetchPortfolioData();
        renderPortfolio(portfolioData ? portfolioData[0] : null);
    } catch (error) {
        console.error('포트폴리오 데이터를 불러오는 중 오류 발생:', error);
        renderPortfolio(null); // 오류 발생 시 + 버튼 표시
    }
};


// 서버에서 프로필 데이터를 가져오고 input에 채우는 함수
async function setupProfileInputs() {
    try {
        // 서버에서 프로필 데이터 가져오기
        const response = await fetch('/api/profile'); // API 경로에 맞게 수정
        if (!response.ok) {
            throw new Error('프로필 데이터를 가져오는 데 실패했습니다.');
        }

        const serverProfileData = await response.json();
        // 가져온 데이터를 input에 채우기
        const inputs = document.querySelectorAll(".profile-input");
        inputs.forEach(input => {
            const name = input.getAttribute('name');
            if (serverProfileData[name]) {
                input.value = serverProfileData[name];
            }
        });
        console.log("서버에서 프로필 데이터를 성공적으로 가져왔습니다.");
    } catch (error) {
        console.error('프로필 데이터를 불러오는 중 오류 발생:', error);
        const serverProfileData = profileData;
        // 가져온 데이터를 input에 채우기
        const inputs = document.querySelectorAll(".profile-input");
        inputs.forEach(input => {
            const name = input.getAttribute('name');
            if (serverProfileData[name]) {
                input.value = serverProfileData[name];
            }
        });
    }
}

// 서버에서 포트폴리오 데이터를 불러오는 함수
async function fetchPortfolioData() {
    try {
        const response = await fetch('/api/portfolio'); // API 경로 확인 필요
        if (!response.ok) throw new Error('데이터를 가져오는 데 실패했습니다.');

        const portfolioData = await response.json();
        return portfolioData;
    } catch (error) {
        console.error('포트폴리오 데이터를 불러오는 중 오류 발생:', error);
        return null; // 오류 시 null 반환
    }
}

async function deletePortfolioFromServer(id) {
    try {
        const response = await fetch(`/api/portfolio/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) throw new Error('삭제 실패');

        console.log(`Portfolio with id ${id} deleted successfully.`);
        return true; // 성공
    } catch (error) {
        console.error('삭제 요청 중 오류 발생:', error);
        return false; // 실패
    }
}




// 수정 및 저장 버튼 설정
function setupEditAndSaveButtons() {
    const editButton = document.getElementById("edit");
    const saveButton = document.getElementById("save");
    const inputs = document.querySelectorAll(".profile-input");

    // 수정 버튼 클릭: input 활성화
    editButton.addEventListener("click", () => {
        inputs.forEach(input => (input.disabled = false));
        toggleButtonVisibility(editButton, saveButton);
    });

    // 저장 버튼 클릭: input 비활성화 및 데이터 저장
    saveButton.addEventListener("click", () => {
        const formData = {};
        inputs.forEach(input => {
            formData[input.name] = input.value;
            input.disabled = true;
        });
        console.log("저장된 데이터:", JSON.stringify(formData));
        toggleButtonVisibility(saveButton, editButton);
    });
}

// 버튼의 가시성 전환
function toggleButtonVisibility(hideButton, showButton) {
    hideButton.style.display = "none";
    showButton.style.display = "inline";
}



// 포트폴리오 UI 렌더링
function renderPortfolio(data) {
    const portfolioContainer = document.getElementById('portfolio-container');
    const plusButton = document.querySelector('.plus-button');

    if (data) {
        // 데이터가 있으면 포트폴리오 UI 표시
        plusButton.style.display = 'none';
        portfolioContainer.innerHTML = `
            <label class="portfolio-text-1" name="desiredPosition">${data.desiredPosition}</label>
            <label class="portfolio-text-2">${data.createdAt}</label>
            <label class="portfolio-text-2">${data.updatedAt}</label>
            <button class="fix-button" onclick="editPortfolio(${data.id})">수정</button>
            <button class="delete-button" onclick="deletePortfolio(${data.id})">삭제</button>
        `;
    } else {
        // 데이터가 없으면 + 버튼 표시
        portfolioContainer.innerHTML = '';
        plusButton.style.display = 'flex';
    }
}

// 수정 버튼 클릭 시 호출
function editPortfolio(id) {
    console.log(`Edit portfolio with id: ${id}`);


    // 수정 로직 추가
}

function deletePortfolio(id) {
    // 삭제 확인 팝업
    const isConfirmed = confirm('정말로 이 포트폴리오를 삭제하시겠습니까?');
    if (!isConfirmed) return;
    const portfolioContainer = document.getElementById('portfolio-container');
    const plusButton = document.querySelector('.plus-button');

    /*
    //테스트용
    plusButton.style.display = 'flex';
    if (portfolioContainer)  portfolioContainer.innerHTML = '';
    */


    deletePortfolioFromServer(id).then(success => {
        if (success) {
            // 성공적으로 삭제되면 화면에서 제거
            plusButton.style.display = 'flex';
            if (portfolioContainer)  portfolioContainer.innerHTML = '';
            console.log(`Portfolio removed`);
        } else {
            alert('삭제에 실패했습니다. 다시 시도해주세요.');
        }
    });
}
