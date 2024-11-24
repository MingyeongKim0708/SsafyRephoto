<template>
    <div class="login">
        <form class="container">
            <fieldset>
                <legend>로그인</legend>
                <div class="inputform">
                    <div>
                        <label for="id">
                            <p>아이디</p>
                            <input type="text" id="id" placeholder="아이디" required v-model = "user.userId">
                        </label>
                    </div>
                    <div>
                        <label for="password">
                            <p>비밀번호</p>
                            <input type="password" id="password" placeholder="비밀번호" required v-model = "user.userPassword">
                        </label>
                    </div>
                    <div>
                        <button type="button" class="btn btn-success" @click.prevent.stop="login">로그인</button>
                        <button type="button" class="btn btn-success" @click.prevent.stop="goRegist">회원 가입</button>
                    </div>
                </div>
            </fieldset>
        </form>
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
.login{
    width: 1200px;
    height: 100%; /* 전체 화면을 꽉 채우기 */
    margin: 0;
    display: flex;
    justify-content: center; /* 가로 중앙 정렬 */
    align-items: center; /* 세로 중앙 정렬 */
}

.container {
    width : 700px;
    height : 700px;
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
    width: 500px;
    height: 50px;
    margin:5px;
    margin-left:0px;
}

p{
    margin-bottom: 0px;
    font-size: 20px;
    font-weight: bold;
}

label{
    text-align: left;
}

.inputform{
    display: flex;
    flex-direction: column;
    align-items: center;
    margin:50px;
}

button{
    margin:5px;
    height: 50px;
    width: 200px;
    background-color: green;
    color:white;
}
</style>