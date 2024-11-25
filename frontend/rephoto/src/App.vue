<template>
  <div id="app">
    <TheHeaderNav />
    <main class="main-content">
      <RouterView />
      <!-- <div v-if="isLoading" id="preloader">Loading...</div>
      <div v-else>
        <slot></slot>
      </div>
      <div v-if="isLoading" id="preloader">
        <div class="line"></div>
      </div> -->
    </main>
    <footer class="footer">
      <TheFooter />
      <button class="scroll-top" @click="scrollToTop">
          ↑
      </button>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import TheHeaderNav from "./components/common/TheHeaderNav.vue";
import TheFooter from "./components/common/TheFooter.vue";

import AOS from 'aos';
import 'aos/dist/aos.css';
import GLightbox from 'glightbox';
import 'glightbox/dist/css/glightbox.css';

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
    once: true,
    mirror: false,
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

  height: 100vh;
  width: 100%;

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
.main-content {
  flex: 1; /* 나머지 공간을 채우도록 */

  display: flex;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 0;
}


.scroll-top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  display: none;
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
