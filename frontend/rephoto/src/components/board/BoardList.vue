<template>
  <div class="container-fluid" id="total">
    <h4>게시글 목록</h4>
    <BoardSearchInput />
    <hr />
    <div>
      <button>
        <RouterLink :to="`/board/create`">게시글 작성</RouterLink>
      </button>
    </div>

    <!-- Gallery Section -->
  <section id="gallery" class="gallery section">
    <div class="container-fluid" data-aos="fade-up" data-aos-delay="100">
      <div class="row gy-4 justify-content-center">
        <div class="col-xl-3 col-lg-4 col-md-6" v-for="board in currentPageBoardList" :key="board.boardId">
          <div class="gallery-item h-100">
            <img :src="getPhotoUrl(board.photoUuid)" :alt="board.boardTitle" class="img-fluid" />
            <div class="gallery-links d-flex align-items-center justify-content-center">
              <RouterLink :to="`/board/${board.boardId}`" class="details-link">
                <i class="bi bi-link-45deg"></i>
              </RouterLink>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- End Gallery Section -->

  <!-- Pagination -->
  <nav aria-label="Page navigation">
    <ul class="pagination d-flex justify-content-center">
      <li class="page-item"><a class="page-link" @click.prevent="currentPage--" :class="{ disabled: currentPage <= 1 }"
          href="#">&lt;</a></li>
      <li class="page-item" :class="{ active: currentPage == page }" v-for="page in pageCount" :key="page">
        <a href="#" class="page-link" @click.prevent="clickPage(page)">{{ page }}</a>
      </li>
      <li class="page-item"><a class="page-link" @click.prevent="currentPage++"
          :class="{ disabled: currentPage >= pageCount }" href="#">&gt;</a></li>
    </ul>
  </nav>





    <hr />
    <!-- <div class="gallery">
      <div v-for="board in currentPageBoardList" :key="board.boardId" class="gallery-item">
        <img :src="getPhotoUrl(board.photoUuid)" :alt="board.boardTitle" class="gallery-image" />
        <div class="gallery-info">
          <h5>{{ board.boardTitle }}</h5>
          <p>{{ board.userNick }}</p>
          <p>조회수: {{ board.viewCnt }}</p>
          <RouterLink :to="`/board/${board.boardId}`">상세보기</RouterLink>
        </div>
      </div>
    </div> -->



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
const pageCount = computed(() => {
  return Math.ceil(store.boardList.length / perPage)
})
const clickPage = function (page) {
  currentPage.value = page
}

const currentPageBoardList = computed(() => {
  return store.boardList.slice((currentPage.value - 1) * perPage, currentPage.value * perPage)
})


// 사진 URL 생성 함수
const getPhotoUrl = (photoUuid) => `http://localhost:8080/img/${photoUuid}`;


</script>



<style scoped>
.container-fluid {
  padding-right: 15px;
  padding-left: 15px;
}

.row {
  margin-right: 0;
  margin-left: 0;
}

.gallery .gallery-item {
  position: relative;
  overflow: hidden;
  border-radius: 10px;
  max-width: 100%; /* 부모 컨테이너를 초과하지 않도록 설정 */
}

.gallery .gallery-item img {
  width: 100%;
  height: 200px;
  /* 정사각형 크기 설정 */
  object-fit: cover;
  /* 이미지 비율 유지하며 잘라내기 */
  object-position: center;
  /* 중앙을 기준으로 자르기 */
  transition: transform 0.3s;
}

.gallery .gallery-links {
  position: absolute;
  inset: 0;
  opacity: 0;
  transition: all ease-in-out 0.3s;
  background: rgba(0, 0, 0, 0.6);
  z-index: 3;
}

.gallery .gallery-links .details-link {
  font-size: 30px;
  line-height: 0;
  color: rgba(255, 255, 255, 0.5);
  transition: 0.3s;
}

.gallery .gallery-links .details-link:hover {
  color: #ffffff;
}

.gallery .gallery-item:hover .gallery-links {
  opacity: 1;
}

.gallery .gallery-item:hover img {
  transform: scale(1.1);
}

.pagination {
  margin-top: 20px;
}

#total  {
  overflow-x: hidden;
}
</style>
