//부모 클래스 가장 앞 이니셜 따서 버튼 이름들 지정
//button_ci=>container_introdce
const frame = document.querySelector(".form");



const ceButton = document.getElementById("ce_button");
const clButton = document.getElementById("cl_button");
const crButton = document.getElementById("cr_button");
const cxButton = document.getElementById("cx_button");
const ccButton = document.getElementById("cc_button");
const csButton = document.getElementById("cs_button");

ceButton.addEventListener("click", function () {
  let educationDiv = document.querySelector(".form .education");
  ceButton.classList.toggle("activate");
  if (educationDiv) {
    // 이미 요소가 존재하면 제거
    frame.removeChild(educationDiv);
  } else {
    // 요소가 없으면 추가
    educationDiv = document.createElement("form");
    educationDiv.classList.add("education");
    educationDiv.innerHTML = `
<div class="maincontainer">
  <div class="heading">
  <div class="heading-2">학력</div>
  <div class="container-9">
    <div class="label-12">
      <img class="image-14" src="img/image-30.png" />
      <div class="text-wrapper-18">고등학교 미만 졸업</div>
    </div>
  </div>
  </div>

<div class="mainframe">
  <div class="container-8">
    <div class="image-wrapper"><img class="image-5" src="img/image-27.png" /></div>
    <div class="background-border-22">
      <div class="label-6">
        <p class="div-4"><span class="span">학교명 </span> <span class="text-wrapper-5">*</span></p>
      </div>
    </div>
    <div class="overlap-group-4">
      <img class="image-13" src="img/image-28.png" />
      <div class="paragraph-wrapper">
        <div class="paragraph">
          <div class="text-wrapper-16">학교구분</div>
          <div class="text-wrapper-17">*</div>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
<button class="plusbutton">
  <img class="image-6" src="img/image-26.png" />
  <div class="text-wrapper-7">추가</div>
</button>

      `;

    frame.appendChild(educationDiv);
  }
});


clButton.addEventListener("click", function () {
  let lisenceDiv = document.querySelector(".form .lisence");//
  clButton.classList.toggle("activate");
  if (lisenceDiv) {
    // 이미 요소가 존재하면 제거
    frame.removeChild(lisenceDiv);
  } else {
    lisenceDiv = document.createElement("form");
    lisenceDiv.classList.add("lisence");
    lisenceDiv.innerHTML = `
      <div class="maincontainer">
        <div class="heading">
          <div class="heading-2">자격증</div>
        </div>
        <div class="mainframe">
          <div class="container-2">
            <div class="background-border-18">
              <img class="image-12" src="img/image-24.png" />
              <div class="label-11">
                <p class="div-6"><span class="span">자격증 명 </span> <span class="text-wrapper-5">*</span></p>
              </div>
            </div>
            <div class="background-border-19">
              <div class="label-2">발행처</div>
            </div>
            <div class="background-border-20">
              <div class="input">
                <div class="container-3">
                  <div class="text-wrapper-6">2017.10</div>
                </div>
              </div>
              <div class="label-2">취득월</div>
            </div>
            <div class="image-wrapper"><img class="image-5" src="img/image-27.png" /></div>
          </div>
        </div>
<button class="plusbutton">
  <img class="image-6" src="img/image-26.png" />
  <div class="text-wrapper-7">추가</div>
</button>
      </div>`


    frame.appendChild(lisenceDiv);
  }
});


crButton.addEventListener("click", function () {
  let educationDiv = document.querySelector(".form .reward");//
  crButton.classList.toggle("activate");
  if (educationDiv) {
    // 이미 요소가 존재하면 제거
    frame.removeChild(educationDiv);
  } else {
    rewardDiv = document.createElement("form");
    rewardDiv.classList.add("reward");
    rewardDiv.innerHTML = `
        
        
        
        
        `



    frame.appendChild(educationDiv);
  }
});


cxButton.addEventListener("click", function () {
  let educationDiv = document.querySelector(".form .education");//
  cxButton.classList.toggle("activate");
  if (educationDiv) {
    // 이미 요소가 존재하면 제거
    frame.removeChild(educationDiv);
  } else {



    frame.appendChild(educationDiv);
  }
});


ccButton.addEventListener("click", function () {
  let educationDiv = document.querySelector(".form .education");//
  ccButton.classList.toggle("activate");
  if (educationDiv) {
    // 이미 요소가 존재하면 제거
    frame.removeChild(educationDiv);
  } else {



    frame.appendChild(educationDiv);
  }
});


csButton.addEventListener("click", function () {
  let skillDiv = document.querySelector(".form .skill");//
  csButton.classList.toggle("activate");
  if (skillDiv) {
    // 이미 요소가 존재하면 제거
    frame.removeChild(skillDiv);
  } else {



    frame.appendChild(skillDiv);
  }
});