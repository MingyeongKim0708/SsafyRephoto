<template>
  <div>
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

</style>