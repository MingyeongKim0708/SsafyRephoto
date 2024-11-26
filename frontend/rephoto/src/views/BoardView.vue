<template>
  <div>
    <RouterLink :to="`/board`" id="title" class="custom-font">
      <h2 class="text-center">REPHOTO : GALLERY</h2>
    </RouterLink>
    <RouterView />
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router';
</script>

<style scoped>
/* @font-face 정의 */
@font-face {
    font-family: 'Hahmlet-Regular';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2110@1.0/Hahmlet-Regular.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}

/* 커스텀 폰트 스타일 */
.custom-font h2 {
  font-family: 'Hahmlet-Regular', sans-serif; /* 적용할 폰트 */
  font-size: 28px; /* 글씨 크기 */
  color: #fff; /* 텍스트 색상 */
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.5); /* 텍스트 강조 효과 */
}

#title {
  text-decoration: none;
  color: #fff;
}
</style>
