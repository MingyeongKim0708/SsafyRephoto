<template>
  <div class="header">
    <!-- 로고 섹션 -->
    <div class="header-logo">
      <a href="/board" class="logo d-flex align-items-center">
        <img src="../../assets/img/REPHOTO-logo-longW.png" alt="REPHOTO Logo"/>
      </a>
    </div>

    <!-- 사용자 정보 섹션 -->
    <div class="header-user-info">
      <div v-if="store.isLogin" class="user-info">
        <div class="greeting">
          <RouterLink
            :to="{
              name: 'profile',
              params: { userId: store.loginUser.userId, userNick: store.loginUser.userNick },
            }"
            class="user-link"
          >
            {{ store.loginUser.userNick }}
          </RouterLink>
          님 <span class="greeting-small">안녕하세요!</span>
        </div>
        <div class="user-actions">
          <span class="text logout" @click.prevent.stop="logout">로그아웃</span>
          <span class="text quit" @click.prevent.stop="quit">회원탈퇴</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";

const store = useUserStore();

const logout = function () {
  store.logout();
};

const quit = function () {
  const check = confirm("정말 탈퇴하시겠습니까?");
  if (check) {
    store.quit();
  }
};
</script>

<style scoped>
/* Header Styles */
.header {
  display: flex;
  justify-content: space-between; /* 로고와 사용자 정보 양쪽 배치 */
  align-items: center;
  background-color: var(--background-color);
  color: var(--default-color);
  padding: 20px;
  border-bottom: 1px solid var(--background-color);
}

/* 로고 섹션 */
.header-logo .logo {
  display: flex;
  align-items: center;
  text-decoration: none;
}

.header-logo .logo img {
  max-height: 36px;
  margin-left: 8px;
}

/* 사용자 정보 섹션 */
.header-user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* 사용자 정보 오른쪽 정렬 */
  text-align: right;
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 5px;
}

.greeting {
  display: flex;
  flex-wrap: nowrap; /* "OO님 안녕하세요" 한 줄 유지 */
  align-items: center;
  font-size: 14px;
}

.greeting-small {
  margin-left: 5px; /* "OO님"과 "안녕하세요" 사이 간격 */
}

.user-actions {
  display: flex;
  gap: 10px;
}

.user-actions .text {
  cursor: pointer;
  color: var(--default-color);
  font-size: 14px;
}

.user-actions .text:hover {
  color: var(--accent-color);
}

.user-link{
  color: var(--accent-color);
  font-size: 14px;
  font-weight: bold;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-decoration: none;
}

.user-link:hover {
  color: var(--default-color)
}

.logout, .quit{
  font-family: var(--default-font);
    font-size: 12px;
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}


.logout {
    background-color: #2776a7;
    color: var(--default-color);
    margin-right: 5px;
}

.logout:hover {
    background-color: color-mix(in srgb, #2776a7 60%, white 40%);
}

.quit {
    background-color: #a72727;
    color: var(--default-color);
}

.quit:hover {
    background-color: color-mix(in srgb, #a72727 60%, white 40%);
}



/* Responsive Design */
@media (max-width: 768px) {
  .header {
    flex-wrap: wrap; /* 줄바꿈 허용 */
    justify-content: space-between; /* 로고와 사용자 정보 분리 */
  }

  .header-user-info {
    align-items: flex-start; /* 사용자 정보 왼쪽 정렬 */
    text-align: left;
    margin-top: 10px;
  }

  .greeting {
    flex-wrap: wrap; /* 폭이 좁아지면 줄바꿈 허용 */
    font-size: 14px;
  }

  .greeting-small {
    margin-left: 0; /* 줄바꿈 시 간격 제거 */
    margin-top: -3px; /* 줄바꿈 후 약간의 위로 당김 */
  }

  .user-actions {
    justify-content: flex-start;
  }
}
</style>
