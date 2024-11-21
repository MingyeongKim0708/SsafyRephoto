<template>
    <div>
    <form>
        <fieldset>
            <legend>로그인</legend>
            <div>
                <label for="title">아이디 : </label>
                <input type="text" id="title" required v-model = "user.userId">
            </div>
            <div>
                <label for="writer">비밀번호 : </label>
                <input type="password" id="writer" required v-model = "user.userPassword">
            </div>
            <div>
                <button @click.prevent.stop="login">로그인</button>
                <button @click.prevent.stop="goRegist">회원 가입</button>
            </div>
        </fieldset>
    </form>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { ref } from 'vue';
import router from '@/router';

const store = useUserStore();
const user = ref({
    userId: "",
    userPassword:""
})

const login = function(){
    store.login(user.value)
}
const goRegist = function(){
    router.push({"name":"regist"})
}
</script>

<style scoped>

</style>