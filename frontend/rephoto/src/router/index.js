import UserCreate from '@/components/user/UserCreate.vue'
import UserLogin from '@/components/user/UserLogin.vue'
import UserProfile from '@/components/user/UserProfile.vue'
import BoardView from '@/views/BoardView.vue'
import UserView from '@/views/UserView.vue'
import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:"/",
      name:"user",
      component: UserView
    },
    {
      path:"/user/login",
      name: "login",
      component: UserLogin
    },
    {
      path:"/user/regist",
      name:"regist",
      component: UserCreate
    }
    ,
    {
      path:"/board",
      name:"board",
      component:BoardView
    },
    {
      path:"/user/:userId",
      name:"profile",
      component:UserProfile,
    }
  ],
})

export default router
