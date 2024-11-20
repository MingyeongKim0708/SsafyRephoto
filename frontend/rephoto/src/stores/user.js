import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {

  const loginUser = ref({"userName":null, "userNick":null});
  


  return {  }
})
