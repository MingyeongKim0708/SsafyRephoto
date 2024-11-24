<template>
    <div class="regist">
        <form @submit.prevent.stop="registUser" class="container">
            <fieldset>
                <legend>회원 가입</legend>
                <div class="inputform">
                    <div>
                        <label for="userId">
                            <p class="type">아이디</p>
                            <div class="input">
                                <input type="text" id="userId" v-model.trim="userId">
                                <button class="btn btn-primary" @click="check('user_id',userId)">중복 확인</button>
                            </div>
                            <p v-if="store.idCheck==0">중복 검사를 실시해 주세요</p>
                            <p v-if="store.idCheck==1" style="color:red">중복된 아이디입니다.</p>
                            <p v-if="store.idCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                        </label> 
                    </div>
                    <div>
                        <label for="userPassword">
                            <p class="type">비밀번호</p>
                            <div class="input">
                                <input type="password" id="userPassword" v-model.trim="userPassword">
                            </div>
                        </label>
                    </div>
                    <div>
                        <label for="userEmail">
                            <p class="type">이메일</p>
                            <div class="input">
                                <input type="email" id="userEmail" v-model.trim="userEmail">
                                <button class="btn btn-primary" @click="check('user_email',userEmail)">중복 확인</button>
                            </div>
                            <p v-if="store.emailCheck==0">중복 검사를 실시해 주세요</p>
                            <p v-if="store.emailCheck==1" style="color:red">중복된 이메일입니다.</p>
                            <p v-if="store.emailCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                        </label>
                    </div>
                    <div>
                        <label for="userNick">
                            <p class="type">닉네임</p>
                            <div class="input">
                                <input type="text" id="userNick" v-model.trim="userNick">
                                <button class="btn btn-primary" @click="check('user_nick',userNick)">중복 확인</button>
                            </div>
                            <p v-if="store.nickCheck==0">중복 검사를 실시해 주세요</p>
                            <p v-if="store.nickCheck==1" style="color:red">중복된 닉네임입니다.</p>
                            <p v-if="store.nickCheck==2" style="color:blue">중복 검사를 통과했습니다.</p>
                        </label>
                    </div>
                    <div>
                        <label for="userImg">
                            <p class="type">프로필 이미지</p>
                            <input type="file" id="userImg" @change="handleImageUpload" accept="image/*"/>
                            <br>
                            <img v-if="previewUrl" :src="previewUrl" alt="프로필 이미지 미리보기" width="200px" height="200px" />
                        </label>
                    </div>
                    <div>
                        <button id="button" type="submit button" class="btn btn-success">등록</button>    
                    </div>
                </div>
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

label{
    text-align: left;
}

.regist{
    width: 1200px;
    height: 100%; /* 전체 화면을 꽉 채우기 */
    margin: 0;
    display: flex;
    justify-content: center; /* 가로 중앙 정렬 */
    align-items: center; /* 세로 중앙 정렬 */
}

.container {
    width : 700px;
    height : 900px;
    border : 1px solid black;
    border-radius : 30px;
    background-color : rgb(255, 235, 197);
    padding : 0px 15px;
    text-align : center;
    box-sizing : border-box;
    margin : 100px ;
    display: flex;
    flex-direction: column;
    justify-content: baseline; /* 내부 요소를 가로로 중앙 정렬 */
    align-items: center; /* 내부 요소를 세로로 중앙 정렬 */
}

legend{
    text-align: center;
    font-size: 50px;
    color:green;
}

fieldset {
    display: flex;
    flex-direction: column;
    align-items: center; 
    justify-content: center; /* 내부 요소를 가로로 중앙 정렬 */
    width: 500px; 
    height: 500px; 
    padding: 10px;
    margin: 10px 0;
}

input{
    width: 300px;
    height: 40px;
    margin:5px;
    margin-left:0px;
}

.input{
    margin:0px;
    display: flex;
    align-items: center;
}

p{
    margin-bottom: 0px;
}
label{
    text-align: left;
    display: flex;
    flex-direction: column;
    align-items: start;
}

.inputform{
    display: flex;
    flex-direction: column;
    align-items: start;
    margin:10px;
}

.type {
    font-size: 20px;
    font-weight: bold;
}

.button{
    display:flex;
    justify-content: center;
    align-items: center;
}

.btn-success{
    margin-top:10px;
}
</style>
