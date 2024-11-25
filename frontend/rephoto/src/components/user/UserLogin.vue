<template>
  <div class="con">
    <div class="login-container">
      <img src="../../image/background.PNG" alt="" class="logo">
      <form class="login-box">
          <fieldset>
              <legend>로그인</legend>
              <div class="inputform">
                  <div>
                      <label for="id" class="input-group">
                          <p>아이디</p>
                          <input type="text" id="id" placeholder="아이디를 입력하세요" required v-model = "user.userId">
                      </label>
                  </div>
                  <div>
                      <label for="password" class="input-group">
                          <p>비밀번호</p>
                          <input type="password" id="password" placeholder="비밀번호를 입력하세요" required v-model = "user.userPassword">
                      </label>
                  </div>
                  <div>
                      <button type="button" class="login-btn" @click.prevent.stop="login">로그인</button>
                      <p class="signup-link">계정이 없으신가요? <a href="/user/regist">회원가입</a></p>
                  </div>
              </div>
          </fieldset>
      </form>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { ref, onMounted } from 'vue';
import router from '@/router';

const store = useUserStore();
const user = ref({
    userId: "",
    userPassword:""
})

onMounted(()=>{
    user.value.userId=""
    user.value.userPassword=""
})



const login = function(){
    store.login(user.value)
}
const goRegist = function(){
    router.push({"name":"regist"})
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  margin: 0;
  padding: 0;
  overflow-x: hidden; /* 가로 스크롤 제거 */
}
.logo{
  max-width: 100%;
  width: 150px; 
  height: auto;
  margin-bottom: 20px;
}
.con {
  width: 100%;
  height: 100%;
  position: relative;
  text-align: center; /* 가운데 정렬 */
  overflow-x: hidden;
}


.login-container {
  max-width: 100%;
  overflow-x: hidden ;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  font-family: Arial, sans-serif;
}

/* 로그인 박스 */
.login-box {
  width: 300px;
  padding: 20px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.login-box h1 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333333;
}

/* 입력 그룹 */
.input-group {
  margin-bottom: 15px;
  text-align: left;
}

.input-group label {
  font-size: 14px;
  color: #555555;
  margin-bottom: 5px;
  display: block;
}

.input-group input {
  width: 100%;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #cccccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.input-group input:focus {
  border-color: #4caf50;
  outline: none;
}

/* 로그인 버튼 */
.login-btn {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  background: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.login-btn:hover {
  background: #388e3c;
}

/* 회원가입 링크 */
.signup-link {
  margin-top: 15px;
  font-size: 12px;
  color: #555555;
}

.signup-link a {
  color: #4caf50;
  text-decoration: none;
}

.signup-link a:hover {
  text-decoration: underline;
}
</style>