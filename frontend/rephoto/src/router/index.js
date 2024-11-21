import UserCreate from '@/components/user/UserCreate.vue'
import UserLogin from '@/components/user/UserLogin.vue'
import BoardView from '@/views/BoardView.vue'
import UserView from '@/views/UserView.vue'
import { createRouter, createWebHistory } from 'vue-router'

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
    }
  ],
})

export default router
