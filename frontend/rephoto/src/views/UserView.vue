<template>
  <div class="start">
    <div v-if="!store.isLogin">
      <button @click="goLogin">Start</button>
    </div>
    <div v-if="store.isLogin">
      <button @click="goBoard">Start</button>
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
.start{
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url(../image/background.PNG);
  
}
button {
  width: 800px;
  height: 200px;
  background-color: antiquewhite;
  font-size:100px;
  border-radius: 40px;
}
</style>