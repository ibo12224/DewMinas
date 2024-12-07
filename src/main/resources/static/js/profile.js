let ownerEmail = document.getElementById("emailBox").value;
let resumeAddButton = document.getElementById("resumeAddButton");
resumeAddButton.addEventListener("click", (ev)=> {
    alert('이력서를 등록합니다.');
    fetch("/api/resume", {
        method: 'POST',
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify({ email: `${ownerEmail}`})
    }).then(() => {
        location = `/resume-update?email=${ownerEmail}`
    });
});