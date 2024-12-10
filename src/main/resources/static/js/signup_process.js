let emailInputBox = document.getElementById("emailInput");
let dupResultLabel = document.getElementById("dup-check-res");
let dupCheckMessage = ['중복 확인이 필요합니다.', '사용할 수 있는 이메일입니다.', '이미 가입된 이메일입니다.'];
let submitBtn = document.getElementById('submitBtn')

let dupCheckBtn = document.getElementById("dup-check-btn");

emailInputBox.addEventListener("click", (ev) => {
    dupResultLabel.textContent = dupCheckMessage[0];
    dupResultLabel.style.color = "#FF0000";
    submitBtn.disabled = true;
});

dupCheckBtn.addEventListener("click", (ev) => {
    ev.preventDefault();
    let targetEmail = emailInputBox.value;
    if(!targetEmail) {
        alert("이메일을 입력하세요.");
        return
    }
    fetch('/api/dupcheck', { method : 'POST', headers : { "Content-Type" : "application/json"}, body : JSON.stringify({ email : targetEmail})})
    .then((response) => response.json())
    .then((data) => {
        if(data.checkRes == 1) {
            dupResultLabel.textContent = dupCheckMessage[1];
            dupResultLabel.style.color = "#00FF00";
            submitBtn.disabled = false;
        } else {
            dupResultLabel.textContent = dupCheckMessage[2];
            dupResultLabel.style.color = "#FF0000";
            submitBtn.disabled = true;
        }
    }).catch(()=> { alert("에러가 발생했습니다."); });
});

