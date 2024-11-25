import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/comment`

export const useCommentStore = defineStore('comment', () => {

  const commentList = ref([]) 
  

  //댓글 등록
const addComment = (newComment) => {
  axios
      .post(REST_API_URL, {
          data : newComment,
          withCredentials:true
      })
      .then(() => {
          console.log("댓글 갱신")
          router.push({
            name:"boardDetail",
            params:{
              id:newComment.boardId
            }
          })
      })
      .catch((err) => {
          console.error(err);
          alert("댓글 작성에 실패했습니다.");
      });
};



  return { commentList, addComment }
})
