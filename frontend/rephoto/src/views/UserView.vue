<template>
  <div class="con">
    <div class="start-container">
      <div class="logo">📸 Photo Review</div>
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

import { useUserStore } from '@/stores/user';
import {useRouter} from 'vue-router';
const router = useRouter()
const store = useUserStore()
const goLogin = function(){
  router.push({"name":"login"})
}

const goBoard = function(){
router.push({"name":"boardList"})
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.con {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.start-container {

  text-align: center;
  animation: fadeIn 2s ease-in-out;
}

.logo {
  font-size: 3rem;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  margin-bottom: 20px;
}

.tagline {
  font-size: 1.5rem;
  font-weight: 300;
  margin-bottom: 40px;
}

.start-button {
  background: rgba(255, 255, 255, 0.8);
  color: black;
  font-size: 1.2rem;
  font-weight: bold;
  padding: 15px 30px;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  transition: transform 0.3s, box-shadow 0.3s;
}

.start-button:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.5);
}

.start-button:active {
  transform: translateY(0);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}
</style>