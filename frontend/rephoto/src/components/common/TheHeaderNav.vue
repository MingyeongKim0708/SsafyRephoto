<template>
  <div class="header">
    <div>
      <a href="/board" class="logo d-flex align-items-center me-auto me-xl-0">
        <!-- Uncomment the line below if you also wish to use an image logo -->
        <!-- <img src="assets/img/logo.png" alt=""> -->
        <i class="bi bi-camera"></i>
        <h1 class="sitename">REPHOTO</h1>
      </a>
      <span v-if="store.isLogin">

        <RouterLink :to="{'name':'profile', params:{'userId':store.loginUser.userId, 'userNick':store.loginUser.userNick}}">{{store.loginUser.userNick}}</RouterLink>

        <span>님, 안녕하세요!</span>
        <br>
        <button @click.prevent.stop="logout">로그 아웃</button>
        <button @click.prevent.stop="quit">회원 탈퇴</button>
      </span>
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
</style>