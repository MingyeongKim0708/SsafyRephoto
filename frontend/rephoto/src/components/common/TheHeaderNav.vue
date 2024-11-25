<template>
  <div class="header">
    <div class="header-info">
      <a href="/board" class="logo d-flex align-items-center me-auto me-xl-0">
        <img src="../../image/REPHOTO-001-removebg-preview.png" alt="">
      </a>
      <div v-if="store.isLogin">
        <RouterLink :to="{'name':'profile', params:{'userId':store.loginUser.userId, 'userNick':store.loginUser.userNick}}">{{store.loginUser.userNick}}</RouterLink>
        <span>님, 안녕하세요!</span>
        <br>
        <span class = "text" @click.prevent.stop="logout">로그아웃  </span>
        <span class = "text" @click.prevent.stop="quit">회원탈퇴</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';

const store = useUserStore();
const logout = function () {
  store.logout()
}
const quit = function () {
  const check = confirm('정말 탈퇴하시겠습니까?')
  if (check) {
    store.quit()
  }
}

const isMobileNavActive = ref(false);
const isDropdownActive = ref(false);

function toggleMobileNav() {
  isMobileNavActive.value = !isMobileNavActive.value;
}

function closeMobileNav() {
  if (isMobileNavActive.value) {
    isMobileNavActive.value = false;
  }
}

function toggleDropdown() {
  isDropdownActive.value = !isDropdownActive.value;
}
</script>

<style scoped>
.mobile-nav-active {
  overflow: hidden;
}

.mobile-nav-active .mobile-nav-toggle {
  color: #fff;
  position: absolute;
  font-size: 32px;
  top: 15px;
  right: 15px;
  margin-right: 0;
  z-index: 9999;
}

.logo{
  text-decoration: none;
}
.mobile-nav-active .navmenu {
  position: fixed;
  overflow: hidden;
  inset: 0;
  background: rgba(33, 37, 41, 0.8);
  transition: 0.3s;
}

.mobile-nav-active .navmenu>ul {
  display: block;
}

.text{
  color: white;
}
header{
  padding: 0;
}
.header-info{
  height:100%;
  width: 100%;
  padding:0 20px 0 20px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

</style>