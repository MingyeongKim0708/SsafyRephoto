<template>
    <div>
        <h4>회원 가입</h4>
        <form @submit.prevent.stop="registUser">
            <fieldset>
                <legend>정보 입력</legend>
                <div>
                    <label for="userId">아이디 : </label>
                    <input type="text" id="userId" v-model.trim="userId">
                    <button @click="check('user_id',userId)">중복 확인</button>
                    <p v-if="store.idCheck==0">중복 검사를 실시해 주세요</p>
                    <p v-if="store.idCheck==1" style="color:red">중복된 아이디입니다.</p>
                    <p v-if="store.idCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                </div>
                <div>
                    <label for="userPassword">비밀번호 : </label>
                    <input type="password" id="userPassword" v-model.trim="userPassword">
                </div>
                <div>
                    <label for="userEmail">이메일 : </label>
                    <input type="email" id="userEmail" v-model.trim="userEmail">
                    <button @click="check('user_email',userEmail)">중복 확인</button>
                    <p v-if="store.emailCheck==0">중복 검사를 실시해 주세요</p>
                    <p v-if="store.emailCheck==1" style="color:red">중복된 이메일입니다.</p>
                    <p v-if="store.emailCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                </div>
                <div>
                    <label for="userNick">닉네임 : </label>
                    <input type="text" id="userNick" v-model.trim="userNick">
                    <button @click="check('user_nick',userNick)">중복 확인</button>
                    <p v-if="store.nickCheck==0">중복 검사를 실시해 주세요</p>
                    <p v-if="store.nickCheck==1" style="color:red">중복된 닉네임입니다.</p>
                    <p v-if="store.nickCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                </div>
                <div>
                    <label for="userImg">프로필 이미지:</label>
                    <input type="file" id="userImg" @change="handleImageUpload" accept="image/*"/>
                    <br>
                    <img v-if="previewUrl" :src="previewUrl" alt="프로필 이미지 미리보기" width="200px" height="200px" />
                </div>
                <button type="submit">등록</button>
            </fieldset>
        </form>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import {useUserStore} from '@/stores/user';
import { onBeforeRouteLeave } from 'vue-router';

const store = useUserStore();

const previewUrl = ref("")

const userId = ref('')
const userPassword = ref('')
const userEmail = ref('')
const userNick = ref('')

const userProfile = ref(null)

const handleImageUpload = function(event){
  const file = event.target.files[0];
  userProfile.value = file;
  if (file) {
    previewUrl.value = URL.createObjectURL(file);
  }
};

watch(userId,()=>{
    store.setIdCheck(0);
})

watch(userNick,()=>{
    store.setNickCheck(0);
})

watch(userEmail,()=>{
    store.setEmailCheck(0);
})

const registUser = function(){
    if(store.idCheck==2 && store.nickCheck==2 && store.emailCheck==2){
        store.registUser(userId.value, userPassword.value, userNick.value, userEmail.value, userProfile.value)
    }
}

const check = function(condition,word){
    store.check(condition,word)
}

onBeforeRouteLeave(()=>{
    store.setIdCheck(0);
    store.setNickCheck(0);
    store.setEmailCheck(0);
})
</script>

<style scoped></style>
