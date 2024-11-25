import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/board`

export const useBoardStore = defineStore('board', () => {

  const boardList = ref([]) //게시글 목록을 스토어에서 관리하겠다~
  
  const getBoardList = function () {
    axios.get(`${REST_API_URL}`, {
      withCredentials:true
    })
      .then((response) => {
        console.log(response.data)
        console.log(boardList.value)
        boardList.value = response.data;
        
      })
      .catch((error) => {
        console.error("게시글 목록 불러오기 실패:", error);
      });
  }

  //게시글 등록
  const createBoard = function (file,userNick,boardTitle,boardInfo) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("userNick", userNick);
    formData.append("boardTitle", boardTitle);
    formData.append("boardInfo", boardInfo);
    console.log(formData);

    axios({
      url: REST_API_URL,
      method: 'POST',

      withCredentials:true,
      data: formData,
      headers: {
        "Content-Type" : "multipart/form-data",
      }
    })
      .then(() => {
        console.log("완료")
        router.push({ name: 'boardList' }) // 등록한 boardDetail로 이동하고 싶은데...
      })
      .catch(() => {
        console.log("실패")
      })
  }
  const board = ref({})

  const getBoard = function (id) {
    axios.get(`${REST_API_URL}/${id}`,
      {
        withCredentials:true
      }
    )
      .then((response) => {
        board.value = response.data
      })
  }

  const updateBoard = function () {
    axios.put(REST_API_URL, board.value,
      {
        withCredentials:true
      })
      .then(() => {
        router.push({ name: 'boardList' })
      })
  }

  const searchBoardList = function (searchCondition) {
    axios.get(REST_API_URL, {
      params: searchCondition,
      withCredentials:true
    })
      .then((res) => {
        boardList.value = res.data
      })
  }

  // 게시글 삭제
const deleteBoard = (id) => {
  axios
      .delete(`${REST_API_URL}/${id}`,
        {
          withCredentials:true
        }
      )
      .then(() => {
          router.push({ name: "boardList" });
      })
      .catch((err) => {
          console.error(err);
          alert("게시글 삭제에 실패했습니다.");
      });
};

  return { boardList, getBoardList, createBoard, board, getBoard, updateBoard, searchBoardList, deleteBoard }
})
