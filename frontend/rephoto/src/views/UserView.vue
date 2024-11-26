<template>
  <div class="carousel-container">
    <!-- Carousel Section -->
    <div class="carousel">
      <div
        v-for="(image, index) in images"
        :key="index"
        :class="['carousel-slide', { active: index === currentIndex }]"
        :style="{ backgroundImage: `url(${image})` }"
      ></div>
    </div>

    <!-- Start Container -->
    <div class="start-container">
      <img src="../assets/img/REPHOTO-logo-longW.png" alt="REPHOTO Logo" class="logo" />
      <div class="tagline">Explore and Share Stunning Moments</div>
      <div v-if="!store.isLogin">
        <button class="start-button" @click="goLogin">Start</button>
      </div>
      <div v-if="store.isLogin">
        <button class="start-button" @click="goBoard">Start</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";

const router = useRouter();
const store = useUserStore();

const images = ref([
  new URL('@/assets/img/gallery/gallery-1.jpg', import.meta.url).href,
  new URL('@/assets/img/gallery/gallery-2.jpg', import.meta.url).href,
  new URL('@/assets/img/gallery/gallery-3.jpg', import.meta.url).href,
]);

const currentIndex = ref(0);

const goLogin = function () {
  router.push({ name: "login" });
};

const goBoard = function () {
  router.push({ name: "boardList" });
};

onMounted(() => {
  setInterval(() => {
    currentIndex.value = (currentIndex.value + 1) % images.value.length;
  }, 5000); // 5초마다 이미지 변경
});
</script>

<style scoped>
/* @font-face 정의 */
@font-face {
    font-family: 'Hahmlet-Regular';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2110@1.0/Hahmlet-Regular.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}

/* General Styles */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.carousel-container {
  position: relative;
  height: 50vh;
  overflow: hidden;
}

/* Carousel Styles */
.carousel {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.carousel-slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  object-fit: cover;
  opacity: 0;
  transition: opacity 1.5s ease, filter 1.5s ease;
  filter: brightness(90%);
}

.carousel-slide.active {
  opacity: 1;
  filter: brightness(40%);
}

/* Start Container */
.start-container {
  position: relative;
  z-index: 10;
  text-align: center;
  color: #fafafa;
  animation: fadeIn 2s ease-in-out;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.logo {
  margin-bottom: 20px;
  width: 30%;
}

.tagline {
  font-size: 1rem;
  font-weight: 300;
  margin-bottom: 40px;
  font-family: 'Hahmlet-Regular', sans-serif;
  color: #fff;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.5);
}

/* Updated Start Button */
.start-button {
  font-family: var(--default-font);
  font-size: 14px;
  padding: 10px 20px;
  color: var(--default-color);
  background-color: var(--surface-color);
  border: solid var(--accent-color);
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.start-button.active {
  background-color: var(--accent-color);
  color: white;
}

.start-button:hover {
  background-color: color-mix(in srgb, var(--accent-color) 80%, white 20%);
  color: white;
}

/* Fade-in Animation */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>
