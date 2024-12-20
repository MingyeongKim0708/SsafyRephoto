import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'


const REST_API_URL = `http://localhost:8080/user`
export const useUserStore = defineStore('user', () => {

  const loginUser = ref({"userId":'', "userNick":''});                  // 로그인했는가
  const user = ref({})                                                  // 프로필을 보기 위한 유저정보
  const isLogin = computed(()=>loginUser.value.userId!=='');            // 로그인했는가
  const idCheck = ref(0);                                               // 아이디 중복확인
  const nickCheck = ref(0);                                             // 닉네임 중복확인
  const emailCheck = ref(0);                                            // 이메일 중복확인
  const login = function(user){                                         // 로그인 요청
    axios({
      url: `${REST_API_URL}/login`,
      method: 'POST',
      data: user,
      withCredentials:true
    })
    .then((response)=>{
      loginUser.value.userId=user.userId
      loginUser.value.userNick=response.data
      router.push({'name':'boardList'})                                   // 성공 시 boardList
    })
    .catch(()=>{
      alert("아이디 혹은 비밀번호가 틀렸습니다.")
      router.push({'name':'login'}) // loginview에서 onbeforerouteleave쓰자
    })
  };

  const logout = function(){
    axios({
      url:`${REST_API_URL}/logout`,
      method: 'GET',
      withCredentials:true      
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
      method: 'DELETE',
      withCredentials:true
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
      },
      withCredentials:true
    })
    .then(()=>{
      router.push({'name':'login'})
    })
    .catch(()=>{
      alert("중복 검사 혹은 입력 정보를 다시 확인해주세요")
      console.log("회원가입 실패")
    })
  } 
  const emitUser = function(userId,userPassword,userNick,userEmail,file){
    const formData = new FormData();
    formData.append("file", file);
    formData.append("userId", userId);
    formData.append("userPassword", userPassword);
    formData.append("userNick", userNick);
    formData.append("userEmail", userEmail);
    axios({
      url:`${REST_API_URL}`,
      method:"PUT",
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
      withCredentials:true
    })
    .then(()=>{
      loginUser.value.userNick = userNick;
      router.push({'name':'profile',params:{'userId':userId, 'userNick':userNick}})
    })
    .catch(()=>{
      console.log("정보 수정 실패")
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
      withCredentials:true
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
      method:'GET',
      withCredentials:true
    })
    .then((response)=>{
      console.log(response.data)
      user.value = response.data
    })
    .catch(()=>{
      console.log("해당 유저 못가져옴")
    })
  }

  return { loginUser,isLogin,idCheck, emailCheck,nickCheck, user, login, logout, quit, registUser, emitUser, setIdCheck, setNickCheck, setEmailCheck, check, getUser}
},
{
  persist:true,
}
)
