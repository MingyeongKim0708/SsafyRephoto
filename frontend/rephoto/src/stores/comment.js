import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/comment`

export const useCommentStore = defineStore('comment', () => {

  const commentList = ref([]) 
  

  //댓글 등록
  const addComment = function(newCommentData){
    console.log(newCommentData)
    axios({
      url: `${REST_API_URL}`,
      method: `POST`,
      data: newCommentData,
      withCredentials:true
    })
    .then(() => {
        console.log("댓글 갱신")
        console.log(newCommentData)
        router.go()
    })
    .catch((err) => {
        console.log(newCommentData)
        console.error(err);
        alert("댓글 작성에 실패했습니다.");
    });
  };



  return { commentList, addComment }
})
