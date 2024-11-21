<template>
    <div>
      <img :src="`http://localhost:8080/user/userImg/${userUuid}`" alt="Profile">
    </div>
</template>

<script setup>
import {useRoute} from 'vue-router'
import {onMounted, ref} from 'vue'
import { useUserStore } from '@/stores/user';

const store = useUserStore();
const route = useRoute()
const userId = ref(route.params.userId)

const imageUrl = ref('')
const userUuid = ref('')
onMounted(async () => {
  try {
    store.getUser(userId.value); // 사용자 정보 가져오기

    userUuid.value = store.user.userUuid;

  } catch (error) {
    console.error('데이터 가져오기 실패:', error);
  }
});
</script>

<style scoped>

</style>