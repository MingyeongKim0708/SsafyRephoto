<template>
  <div class="page-content profile ">
    <div class="profile-detail">
      <div class="profile-header">
        <img :src="`http://localhost:8080/user/userImg/${userUuid}`" alt="Profile" class="profile-image">
        <div class="profile-info">
          <h2>{{userNick}}</h2>
          <p>게시글 수 : {{ store2.boardList.length }}</p>
        </div>
      </div>
      <div v-if="store.loginUser.userId === userId" class="emit">
        <button class="btn btn-info" @click="goEmit">회원 정보 수정</button>
      </div>
    </div>

    <div class="container-fluid" id="total">
      <h4>{{userNick}}님의 게시글</h4>
      <UserOrderInput :userNick="userNick"/>
      <hr/>
      <section id="gallery" class="gallery section">
        <div class="container-fluid" data-aos="fade-up" data-aos-delay="100" :key="currentPage">
          <div class="row gy-4 justify-content-center">
            <div class="col-xl-3 col-lg-4 col-md-6" v-for="board in currentPageBoardList" :key="board.boardId">
              <div class="gallery-item h-100">
                <img :src="getPhotoUrl(board.photoUuid)" :alt="board.boardTitle" class="img-fluid"/>
                <div class="gallery-links d-flex flex-column align-items-center justify-content-center">
                  <p class="gallery-title">{{ truncateText(board.boardTitle, 15) }}</p>
                  <RouterLink :to="`/board/${board.boardId}`" class="details-link">
                    <i class="bi bi-link-45deg"></i>
                  </RouterLink>
                </div>
              </div>
            </div>
          </div>  
        </div>
      </section>
      <nav aria-label="Page navigation">
        <ul class="pagination-custom">
          <li class="page-item">
            <a class="page-link" @click.prevent="goToPage(currentPage - 1)" :class="{disabled: currentPage <= 1}" 
            href="#"></a>
          </li>
          <li class="page-item" :class="{ active: currentPage == page}" v-for="page in pageCount" :key="page">
              <a href="#" class="page-link" @click.prevent="goToPage(page)"></a>
          </li>
          <li class="page-item">
            <a class="page-link" @click.prevent="goToPage(currentPage + 1)" 
            :class="{ disabled: currentPage >= pageCount }" href="#"></a>
          </li>
        </ul>
      </nav>
    </div>
  </div>  
</template>

<script setup>
import {useRoute,useRouter} from 'vue-router'
import {onMounted, ref, computed} from 'vue'
import { useUserStore } from '@/stores/user';
import { useBoardStore } from '@/stores/board';
import UserOrderInput from './UserOrderInput.vue';
import AOS from 'aos';

const route = useRoute();
const router = useRouter();
const store = useUserStore();
const store2 = useBoardStore();
const userId = ref(route.params.userId)
const userNick = ref(route.params.userNick)

const userUuid = computed(()=>store.user.userUuid)
const currentPage = ref(1)
const perPage = 12;


onMounted(() => {
  try {
    store.getUser(userId.value); // 사용자 정보 가져오기
    console.log("Uuid: ",userUuid.value)
    console.log("닉네임:",userNick.value)
    store2.getUserBoardList(userNick.value)
    AOS.refresh();
  } catch (error) {
    console.error('데이터 가져오기 실패:', error);
  }
});

const pageCount = computed(()=>{
    return Math.ceil(store2.boardList.length / perPage)
})

const currentPageBoardList = computed(()=>{
    return store2.boardList.slice((currentPage.value-1)*perPage, currentPage.value*perPage)
})

const goToPage = (page) => {
  if (page >= 1 && page <= pageCount.value) {
    currentPage.value = page;

    // 스크롤을 최상단으로 이동
    window.scrollTo({
      top: 0,
      behavior: "smooth", // 부드러운 스크롤 이동
    });

    // 페이지 변경 후 AOS 새로고침
    setTimeout(() => {
      AOS.refresh();
    }, 0);
  }
};

// 사진 URL 생성 함수
const getPhotoUrl = (photoUuid) => `http://localhost:8080/img/${photoUuid}`;

const goEmit = function(){
  router.push({'name':'emit', params:{'userId':store.loginUser.userId}})
}

// 텍스트 자르기 함수
const truncateText = (text, maxLength) => {
  if (text.length > maxLength) {
    return text.substring(0, maxLength) + '...';
  }
  return text;
};
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

.profile-detail{
  width:100%;
}

.emit{
  margin:10px;
}

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
  max-width: 100%;
  /* 부모 컨테이너를 초과하지 않도록 설정 */
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

.gallery .gallery-title {
  color: #ffffff;
  font-size: 16px;
  font-weight: bold;
  margin: 5px 0;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.gallery .gallery-author {
  color: #bdbdbd;
  /* 회색 톤 */
  font-size: 14px;

  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-decoration: none;

}

.gallery .gallery-author:hover {
  color: #ffffff;
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

#total {
  overflow-x: hidden;
}

.pagination-custom {
  display: flex;
  justify-content: center;
  gap: 10px;
  /* 버튼 간격 */
  margin-top: 20px;
}

.pagination-custom .page-item {
  list-style: none;
}

.pagination-custom .page-link {
  display: inline-block;
  width: 12px;
  height: 12px;
  background-color: color-mix(in srgb, var(--default-color), transparent 85%);
  border-radius: 50%;
  transition: background-color 0.3s;
}

.pagination-custom .page-item.active .page-link {
  background-color: var(--accent-color);
  transform: scale(1.2);
  /* 강조 효과 */
}

.pagination-custom .page-link:hover {
  background-color: var(--accent-color);
}


.pagination-custom .page-link.disabled {
  pointer-events: none;
  opacity: 0.5;
}


</style>
