<template>
  <div>
    <div>
      Logo 
      <span v-if="store.isLogin">
        <RouterLink :to="{'name':'profile', params:{'userId':store.loginUser.userId, 'userNick':store.loginUser.userNick}}">{{store.loginUser.userNick}}</RouterLink>
        <span>님, 안녕하세요!</span>
        <button @click.prevent.stop="logout">로그 아웃</button>
        <button @click.prevent.stop="quit">회원 탈퇴</button>
      </span> 
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';

const store = useUserStore();
const logout = function(){
  store.logout()
}
const quit = function(){
  const check = confirm('정말 탈퇴하시겠습니까?')
  if(check){
    store.quit()
  }
}
</script>

<style scoped>

</style>