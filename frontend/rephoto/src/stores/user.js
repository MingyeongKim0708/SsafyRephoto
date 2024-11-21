import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'


const REST_API_URL = `http://localhost:8080/user`
export const useUserStore = defineStore('user', () => {

  const loginUser = ref({"userId":'', "userNick":''});
  const user = ref({})
  const profile = ref(null)
  const isLogin = computed(()=>loginUser.value.userId!=='');
  const idCheck = ref(0);
  const nickCheck = ref(0);
  const emailCheck = ref(0);
  const login = function(user){
    axios({
      url: `${REST_API_URL}/login`,
      method: 'POST',
      data: user
    })
    .then((response)=>{
      loginUser.value.userId=user.userId
      loginUser.value.userNick=response.data
      console.log(loginUser.value)
      router.push({name:'board'})
    })
    .catch(()=>{
      console.log("실패")
    })
  };

  const logout = function(){
    axios({
      url:`${REST_API_URL}/logout`,
      method: 'GET'
    })
    .then(()=>{
      loginUser.value.userId=''
      loginUser.value.userNick=''
      router.push({'name':'login'})
    }).catch(()=>{
      console.log(" 로그아웃 실패")
    })
  };

  const quit = function(){
    axios({
      url:`${REST_API_URL}/${loginUser.value.userId}`,
      method: 'DELETE'
    })
    .then(()=>{
      loginUser.value.userId=''
      loginUser.value.userNick=''
      router.push({'name':'login'})
    })
    .catch(()=>{
      console.log("회원 탈퇴 실패")
    })
  }

  const registUser = function(userId,userPassword,userNick,userEmail,file){
    const formData = new FormData();
    formData.append("file", file);
    formData.append("userId", userId);
    formData.append("userPassword", userPassword);
    formData.append("userNick", userNick);
    formData.append("userEmail", userEmail);
    console.log(formData)
    axios({
      url:`${REST_API_URL}/regist`,
      method:"POST",
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      }
    })
    .then(()=>{
      router.push({'name':'login'})
    })
    .catch(()=>{
      console.log("회원가입 실패")
    })
  } 

  const setIdCheck = function(value){
    idCheck.value=value;
  }

  const setNickCheck = function(value){
    nickCheck.value=value;
  }

  const setEmailCheck = function(value){
    emailCheck.value=value;
  }

  const check = function(condition,word){
    axios({
      url: `${REST_API_URL}/check/${condition}/${word}`,
      method: 'GET',
    })
    .then(()=>{
      if(condition==='user_id'){
        idCheck.value = 2;
      }
      if(condition==='user_nick'){
        nickCheck.value = 2;
      }
      if(condition==='user_email'){
        emailCheck.value = 2;
      }
    })
    .catch(()=>{
      if(condition==='user_id'){
        idCheck.value = 1;
      }
      if(condition==='user_nick'){
        nickCheck.value = 1;
      }
      if(condition==='user_email'){
        emailCheck.value = 1;
      }
    })
  }
  const getUser = function(userId){
    axios({
      url:`${REST_API_URL}/myPage/${userId}`,
      method:'GET'
    })
    .then((response)=>{
      console.log(response.data)
      user.value = response.data
    })
    .catch(()=>{
      console.log("해당 유저 못가져옴")
    })
  }

  return { loginUser,isLogin,idCheck, emailCheck,nickCheck, user, profile, login, logout, quit, registUser, setIdCheck, setNickCheck, setEmailCheck, check, getUser}
},
{
  persist:true,
}
)