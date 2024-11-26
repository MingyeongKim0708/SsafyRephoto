<template>
    <div class="regist-container">
        <div class="regist">
            <form @submit.prevent.stop="registUser">
                <fieldset class="signup-container">
                    <legend>회원 가입</legend>
                    <div class="form-group">
                        <label for="userId">아이디</label>               
                        <input type="text" id="userId" v-model.trim="userId" placeholder="영어, 숫자, -, _만 가능, 16자 이하">
                        <p v-if="store.idCheck==0">중복 검사를 실시해 주세요</p>
                        <p v-if="store.idCheck==1" style="color:red">중복된 아이디입니다.</p>
                        <p v-if="store.idCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                        <button type="button" @click="check('user_id',userId)">중복 확인</button>
                    </div>
                    <div class="form-group">
                        <label for="userPassword">비밀번호</label>
                        <input type="password" id="userPassword" v-model.trim="userPassword" placeholder="10자 이상, 30자 이하">
                    </div>
                    <div class="form-group">
                        <label for="userEmail">이메일</label>
                        <input type="email" id="userEmail" v-model.trim="userEmail" placeholder="이메일을 입력해주세요">
                        <p v-if="store.emailCheck==0">중복 검사를 실시해 주세요</p>
                        <p v-if="store.emailCheck==1" style="color:red">중복된 이메일입니다.</p>
                        <p v-if="store.emailCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                        <button class="btn btn-primary" @click="check('user_email',userEmail)">중복 확인</button>                           
                    </div>
                    <div class="form-group">
                        <label for="userNick"> 닉네임</label>
                        <input type="text" id="userNick" v-model.trim="userNick" placeholder="16자 이하">
                        <p v-if="store.nickCheck==0">중복 검사를 실시해 주세요</p>
                        <p v-if="store.nickCheck==1" style="color:red">중복된 닉네임입니다.</p>
                        <p v-if="store.nickCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                        <button class="btn btn-primary" @click="check('user_nick',userNick)">중복 확인</button>                            
                    </div>
                    <div class="form-group">
                        <label for="userImg">프로필 이미지 (jpg, jpeg, webp. png 파일만 가능)</label>
                        <input type="file" id="userImg" @change="handleImageUpload" accept="image/*"/>
                        <br>
                        <img v-if="previewUrl" :src="previewUrl" alt="프로필 이미지 미리보기" width="200px" height="200px" />
                    </div>
                    <div>
                        <button id="button" type="submit" class="submit-btn">등록</button>    
                    </div>
                </fieldset>
            </form>
        </div>
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
    } else {
        previewUrl.value = "";
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

<style scoped>

.regist-container {
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: Arial, sans-serif;
  height: 100vh; /* 부모 요소 높이를 화면 전체로 설정 */
  overflow: hidden; /* 넘치는 내용 숨기기 */
}

.regist {
  width: 100%;
  max-width: 500px; /* 최대 너비 제한 */
}

.signup-container {
  max-height: 100vh; /* 내용이 너무 길어지지 않도록 제한 */
  overflow-y: auto; /* 내용이 넘치면 세로 스크롤 활성화 */
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h1 {
    text-align: center;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 15px;
}

label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

input[type="text"],
input[type="password"],
input[type="email"],
input[type="file"] {
    width: 100%;
    padding: 10px;
    margin-bottom: 5px;
    border: 1px solid #ddd;
    border-radius: 4px;
}

button {
    padding: 10px 15px;
    border: none;
    background-color: #007bff;
    color: #fff;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #0056b3;
}

.submit-btn {
    width: 100%;
}

.image-preview {
    margin-top: 10px;
}

.image-preview img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border: 1px solid #ddd;
    border-radius: 50%;
}
</style>
