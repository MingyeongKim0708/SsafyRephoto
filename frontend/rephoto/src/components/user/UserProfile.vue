<template>
    <div>
      <div class="profile-header">
        <img :src="`http://localhost:8080/user/userImg/${userUuid}`" alt="Profile" class="profile-image">
        <div class="profile-info">
          <h3>{{userNick}}</h3>
          <h4>게시글 수 : {{ store2.boardList.length }}</h4>
        </div>
      </div>
      <div v-if="store.loginUser.userId === userId" class="emit">
        <button class="btn btn-info" @click="goEmit">회원 정보 수정</button>
      </div>
    </div>
    <div class="container">
    <h4>{{userNick}}님의 게시글</h4>
    <UserOrderInput :userNick="userNick"/>
    <hr />
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
import {useRoute,useRouter} from 'vue-router'
import {onMounted, ref, computed} from 'vue'
import { useUserStore } from '@/stores/user';
import { useBoardStore } from '@/stores/board';
import UserOrderInput from './UserOrderInput.vue';

const route = useRoute();
const router = useRouter();
const store = useUserStore();
const store2 = useBoardStore();
const userId = ref(route.params.userId)
const userNick = ref(route.params.userNick)

const userUuid = ref('')
const currentPage = ref(1)
const perPage = 12;


onMounted(async () => {
  try {
    await store.getUser(userId.value); // 사용자 정보 가져오기

    userUuid.value = store.user.userUuid;
    console.log("닉네임:",userNick.value)
    store2.getUserBoardList(userNick.value)
  } catch (error) {
    console.error('데이터 가져오기 실패:', error);
  }
});

const pageCount = computed(()=>{
    return Math.ceil(store2.boardList.length / perPage)
})
const clickPage = function(page){
    currentPage.value = page
}

const currentPageBoardList = computed(()=>{
    return store2.boardList.slice((currentPage.value-1)*perPage, currentPage.value*perPage)
})

// 사진 URL 생성 함수
const getPhotoUrl = (photoUuid) => `http://localhost:8080/img/${photoUuid}`;

const goEmit = function(){
  router.push({'name':'emit', params:{'userId':store.loginUser.userId}})
}
</script>

<style scoped>

.profile-image {
  width: 100px;
  height: 100px;
  border-radius: 50%; /* 원형 이미지 */
  margin-right: 20px;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}


img{
  margin:10px;
}

.emit{
  margin:10px;
}

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
}

.profile-info {
  flex: 1;
}

</style>
