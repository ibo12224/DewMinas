const frame = document.querySelector(".form");

document.addEventListener('DOMContentLoaded', function() {
   addFieldSet('skill');
   addFieldSet('workExps');
   addFieldSet('eduExps');
   addFieldSet('academicActivities');
   addFieldSet('awards');
   addFieldSet('educations');
   addFieldSet('licenses');
   window.addEventListener("beforeunload", beforeUnloadHandler);
// 모든 버튼의 상태 복원
  document.querySelectorAll('.toggle-button').forEach(restoreSectionState);

// 버튼 클릭 이벤트 리스너 등록
  document.addEventListener('click', handleToggleButtonClick);
  const savedImageUrl = localStorage.getItem('imageUrl');
  if (savedImageUrl) {
    const imageContainer = document.querySelector('.image-container');
    imageContainer.style.backgroundImage = `url(${savedImageUrl})`;
  }
});



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