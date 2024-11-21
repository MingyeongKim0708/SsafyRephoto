<template>
  <div class="container">
    <h4>게시글 목록</h4>
    <BoardSearchInput />
    <hr />
    <div>
      <button>
        <RouterLink :to="`/board/create`">게시글 작성</RouterLink>
      </button>
    </div>
    <div class="gallery">
      <div 
        v-for="board in currentPageBoardList" 
        :key="board.boardId" 
        class="gallery-item"
      >
        <img 
          :src="getPhotoUrl(board.photoUuid)" 
          :alt="board.boardTitle" 
          class="gallery-image"

        />
        <div class="gallery-info">
          <h5>{{ board.boardTitle }}</h5>
          <p>{{ board.userNick }}</p>
          <p>조회수: {{ board.viewCnt }}</p>
          <RouterLink :to="`/board/${board.boardId}`">상세보기</RouterLink>
        </div>
      </div>
    </div>


    <nav aria-label="Page navigation">
      <ul class="pagination d-flex justify-content-center">
        <li class="page-item"><a class="page-link" @click.prevent="currentPage--" :class="{disabled: currentPage <= 1}" href="#">&lt;</a></li>
        <li class="page-item" :class="{active: currentPage == page}" v-for="page in pageCount" :key="page">
            <a href="#" class="page-link" @click.prevent="clickPage(page)">{{page}}</a>
        </li>
        <li class="page-item"><a class="page-link" @click.prevent="currentPage++" :class="{disabled: currentPage >= pageCount}" href="#">&gt;</a></li>
      </ul>
    </nav>
  </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { computed, onMounted, ref } from 'vue';
import BoardSearchInput from './BoardSearchInput.vue';

const store = useBoardStore();

onMounted(() => {
  store.getBoardList();
});

const currentPage = ref(1)
const perPage = 12;
const pageCount = computed(()=>{
    return Math.ceil(store.boardList.length / perPage)
})
const clickPage = function(page){
    currentPage.value = page
}

const currentPageBoardList = computed(()=>{
    return store.boardList.slice((currentPage.value-1)*perPage, currentPage.value*perPage)
})


// 사진 URL 생성 함수
const getPhotoUrl = (photoUuid) => `http://localhost:8080/img/${photoUuid}`;


</script>

<style scoped>
.board-gallery {
  margin: 20px;
}
.gallery {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 16px;
}
.gallery-item {
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  text-align: center;
}
.gallery-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
}
.gallery-info {
  padding: 10px;
}
.pagination {
  margin-top: 20px;
}</style>
