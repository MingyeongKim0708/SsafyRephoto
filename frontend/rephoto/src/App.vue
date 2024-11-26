<template>
  <div id="app">
    <TheHeaderNav />

    <main class="main-content">
      <div class="container">
        <RouterView />
      </div>

      <!-- 로딩화면 시작-->
      <!-- <div v-if="isLoading" id="preloader">Loading...</div>
      <div v-else>
        <slot></slot>
      </div>
      <div v-if="isLoading" id="preloader">
        <div class="line"></div>
      </div> -->
      <!-- 로딩화면 끝 -->
    </main>

    <footer class="footer">
      <TheFooter />
    </footer>
        
    <button class="scroll-top" :class="{ active: isScrollTopVisible }" @click="scrollToTop">↑</button>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import TheHeaderNav from "./components/common/TheHeaderNav.vue";
import TheFooter from "./components/common/TheFooter.vue";

import AOS from 'aos';
import 'aos/dist/aos.css';
import 'aos/dist/aos.js'
import GLightbox from 'glightbox';
import 'glightbox/dist/css/glightbox.css';

const router = useRouter();
const isScrolled = ref(false);
const isScrollTopVisible = ref(false);

function handleScroll() {
  const scrollY = window.scrollY;
  isScrolled.value = scrollY > 100;
  isScrollTopVisible.value = scrollY > 100;
}

function scrollToTop() {
  window.scrollTo({ top: 0, behavior: 'smooth' });
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll);
  AOS.init({
    duration: 600,
    easing: 'ease-in-out',
    once: false,
    mirror: false,
  });
  router.afterEach(() => {
    AOS.refresh(); // 페이지 전환 시 애니메이션 재적용
  });

  GLightbox({ selector: '.glightbox' });

  // 로딩 상태 변경 후 2초 뒤에 'loaded' 클래스 추가
  setTimeout(() => {
    isLoading.value = false;
    document.getElementById('preloader')?.classList.add('loaded');
  }, 2000);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});

const isLoading = ref(true);
</script>

<style scoped>

/* * {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
} */

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  min-width: 100%;

  /* background-color: #5A5A5A; */
}

header{
  height:120px;
  padding:0px;
}

footer{
  min-height: 120px; /* 유동적 설정 */
  background-color: black;
}
/* MainView가 Header와 Footer 사이의 영역을 채우도록 설정 */
/* 메인 콘텐츠 영역 */
.main-content {

  flex: 1;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 20px;
}

/* 콘텐츠를 감싸는 컨테이너 */
.main-content .container {
  max-width: 1200px; /* 콘텐츠의 최대 너비 */
  width: 100%;
  padding: 20px;
  border-radius: 8px; /* 시각적 강조를 위한 모서리 둥글기 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 약간의 그림자 */
}



/* 스크롤 버튼 스타일 */
.scroll-top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  display: none;
  background-color: var(--accent-color);
  color: var(--contrast-color);
  border: none;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  font-size: 20px;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  z-index: 1000; /* 항상 위에 표시 */
}

.scroll-top.active {
  display: block;
}

#preloader {
  display: flex;
  position: fixed;
  inset: 0;
  width: 100%;
  height: 100vh;
  z-index: 99999;
}

#preloader:before,
#preloader:after {
  content: "";
  background-color: #000000;
  position: absolute;
  inset: 0;
  width: 50%;
  height: 100%;
  transition: all 0.3s ease 0s;
  z-index: -1;
}

#preloader:after {
  left: auto;
  right: 0;
}

#preloader .line {
  position: relative;
  overflow: hidden;
  margin: auto;
  width: 1px;
  height: 280px;
  transition: all 0.8s ease 0s;
}

#preloader .line:before {
  content: "";
  position: absolute;
  background-color: #ffffff;
  left: 0;
  top: 50%;
  width: 1px;
  height: 0%;
  transform: translateY(-50%);
  animation: lineincrease 1000ms ease-in-out 0s forwards;
}

#preloader .line:after {
  content: "";
  position: absolute;
  background-color: color-mix(in srgb, var(--default-color), transparent 95%);
  left: 0;
  top: 0;
  width: 1px;
  height: 100%;
  transform: translateY(-100%);
  animation: linemove 1200ms linear 0s infinite;
  animation-delay: 2000ms;
}

#preloader.loaded .line {
  opacity: 0;
  height: 100% !important;
}

#preloader.loaded .line:after {
  opacity: 0;
}

#preloader.loaded:before,
#preloader.loaded:after {
  animation: preloaderfinish 300ms ease-in-out 500ms forwards;
  display: none;
}

@keyframes lineincrease {
  0% {
    height: 0%;
  }
  100% {
    height: 100%;
  }
}

@keyframes linemove {
  0% {
    transform: translateY(200%);
  }
  100% {
    transform: translateY(-100%);
  }
}

@keyframes preloaderfinish {
  0% {
    width: 50%;
  }
  100% {
    width: 0%;
  }
}
</style>
