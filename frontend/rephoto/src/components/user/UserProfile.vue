<template>
    <div>
      <img :src="`http://localhost:8080/user/userImg/${userUuid}`" alt="Profile">
      <div>닉네임: {{userNick}}</div>
      <div v-if="store.loginUser.userId === userId">
        <button @click="goEmit">회원 정보 수정</button>
      </div>
    </div>
</template>

<script setup>
import {useRoute,useRouter} from 'vue-router'
import {onMounted, ref} from 'vue'
import { useUserStore } from '@/stores/user';

const route = useRoute();
const router = useRouter();
const store = useUserStore();
const userId = ref(route.params.userId)
const userNick = ref(route.params.userNick)

const imageUrl = ref('')
const userUuid = ref('')
onMounted(async () => {
  try {
    await store.getUser(userId.value); // 사용자 정보 가져오기

    userUuid.value = store.user.userUuid;

  } catch (error) {
    console.error('데이터 가져오기 실패:', error);
  }
});

const goEmit = function(){
  router.push({'name':'emit', params:{'userId':store.loginUser.userId}})
}
</script>

<style scoped>

</style>