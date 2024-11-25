<template>
    <div class="emit-container">
        <div class="emit">
            <form @submit.prevent.stop="emitUser">
                <fieldset class="update-container">
                    <legend>정보 수정</legend>
                    <div class="form-group">
                        <label for="userPassword">비밀번호</label>
                        <input type="password" id="userPassword" v-model.trim="userPassword">
                    </div>
                    <div class="form-group">
                        <label for="userEmail">이메일</label>
                        <input type="email" id="userEmail" v-model.trim="userEmail">
                        <p v-if="store.emailCheck==0">중복 검사를 실시해 주세요</p>
                        <p v-if="store.emailCheck==1" style="color:red">중복된 이메일입니다.</p>
                        <p v-if="store.emailCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                        <button class="btn btn-primary" @click="check('user_email',userEmail)">중복 확인</button>                           
                    </div>
                    <div class="form-group">
                        <label for="userNick"> 닉네임</label>
                        <input type="text" id="userNick" v-model.trim="userNick">
                        <p v-if="store.nickCheck==0">중복 검사를 실시해 주세요</p>
                        <p v-if="store.nickCheck==1" style="color:red">중복된 닉네임입니다.</p>
                        <p v-if="store.nickCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                        <button class="btn btn-primary" @click="check('user_nick',userNick)">중복 확인</button>                            
                    </div>
                    <div class="form-group">
                        <label for="userImg">프로필 이미지</label>
                        <input type="file" id="userImg" @change="handleImageUpload" accept="image/*"/>
                        <br>
                        <img v-if="previewUrl" :src="previewUrl" alt="프로필 이미지 미리보기" width="200px" height="200px" />
                    </div>
                    <div>
                        <button id="button" type="submit button" class="btn btn-success">등록</button>    
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import {useUserStore} from '@/stores/user';
import { useRoute,onBeforeRouteLeave } from 'vue-router';

const route = useRoute();
const userId = ref(route.params.userId);
const store = useUserStore();

const previewUrl = ref("")

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

watch(userNick,()=>{
    store.setNickCheck(0);
})

watch(userEmail,()=>{
    store.setEmailCheck(0);
})

const emitUser = function(){
    if(store.nickCheck==2 && store.emailCheck==2){
        store.emitUser(userId.value, userPassword.value, userNick.value, userEmail.value, userProfile.value)
    }
}

const check = function(condition,word){
    store.check(condition,word)
}

onBeforeRouteLeave(()=>{
    store.setNickCheck(0);
    store.setEmailCheck(0);
})
</script>

<style scoped>
.emit-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    font-family: Arial, sans-serif;
}
.update-container {
    width: 400px;
    margin: 50px auto;
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
