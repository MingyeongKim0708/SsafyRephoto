<template>
  <div>
    <TheHeaderNav/>
    <RouterView/>
    <TheFooter/>
  </div>
</template>

<script setup>
  import TheHeaderNav from "./components/common/TheHeaderNav.vue";
  import TheFooter from "./components/common/TheFooter.vue";
</script>

<style scoped>

</style>