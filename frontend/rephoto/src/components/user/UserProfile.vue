<template>
    <div>
    </div>
</template>

<script setup>
import {useRoute} from 'vue-router'
import {onMounted, ref} from 'vue'
import { useUserStore } from '@/stores/user';

const store = useUserStore();
const route = useRoute()
const userId = ref(route.params.userId)

onMounted(async () => {
  try {
    store.getUser(userId.value); // 사용자 정보 가져오기

    const userUuid = store.user.userUuid;
    if (userUuid) {
        store.getProfile(userUuid); // 프로필 정보 가져오기
    }
  } catch (error) {
    console.error('데이터 가져오기 실패:', error);
  }
});
</script>

<style scoped>

</style>