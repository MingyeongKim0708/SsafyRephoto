<template>
  <div>
    <RouterLink :to="`/board`" id="title"><h2 class="text-center">REPHOTO : GALLERY</h2></RouterLink>
    <RouterView/>
  </div>
</template>

<script setup>

import { RouterView } from 'vue-router';

</script>

<style scoped>
#title{
  text-decoration: none;
  color: #fff;
}

</style>