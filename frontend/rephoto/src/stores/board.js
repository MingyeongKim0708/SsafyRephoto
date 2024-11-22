import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/board`

export const useBoardStore = defineStore('board', () => {

  const boardList = ref([]) //게시글 목록을 스토어에서 관리하겠다~
  
  const getBoardList = function () {
    axios.get(`${REST_API_URL}`)
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
  const createBoard = function (board) {
    axios({
      url: REST_API_URL,
      method: 'POST',
      //JSON 형태로 바꿔주지 않았지만 application/json 알아서 이게 적용이 되었다.
      data: board
    })
      .then(() => {
        console.log("완료")
        router.push({ name: 'boardList' })
      })
      .catch(() => {
        console.log("실패")
      })
  }
  const board = ref({})

  const getBoard = function (id) {
    axios.get(`${REST_API_URL}/${id}`)
      .then((response) => {
        board.value = response.data
      })
  }

  const updateBoard = function () {
    axios.put(REST_API_URL, board.value)
      .then(() => {
        router.push({ name: 'boardList' })
      })
  }

  const searchBoardList = function (searchCondition) {
    axios.get(REST_API_URL, {
      params: searchCondition
    })
      .then((res) => {
        boardList.value = res.data
      })
  }

  return { boardList, getBoardList, createBoard, board, getBoard, updateBoard, searchBoardList }
})
