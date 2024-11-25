import UserCreate from '@/components/user/UserCreate.vue'
import UserLogin from '@/components/user/UserLogin.vue'
import UserProfile from '@/components/user/UserProfile.vue'
import BoardView from '@/views/BoardView.vue'
import UserView from '@/views/UserView.vue'
import { createRouter, createWebHistory } from 'vue-router'

import { useUserStore } from '@/stores/user'

import BoardList from '@/components/board/BoardList.vue'
import BoardCreate from '@/components/board/BoardCreate.vue'
import BoardDetail from '@/components/board/BoardDetail.vue'
import UserEmit from '@/components/user/UserEmit.vue'


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
      component: UserLogin,
      beforeEnter: ()=>{
        const store = useUserStore();
        if(store.isLogin){
          return {'name':'boardList'};
        }
      }
    },
    {
      path:"/user/regist",
      name:"regist",
      component: UserCreate,
      beforeEnter: ()=>{
        const store = useUserStore();
        if(store.isLogin){
          return {'name':'boardList'};
        }
      }
    }
    ,
    {
      path:"/user/:userId/:userNick",
      name:"profile",
      component:UserProfile,
      beforeEnter: ()=>{
        const store = useUserStore();
        if(!store.isLogin){
          return {'name':'login'};
        }
      },
    },
    {
      path:"/user/emit/:userId",
      name:"emit",
      component:UserEmit,
      beforeEnter: (to,from)=>{
        const store = useUserStore();
        const routeUserId = to.params.userId
        if(!store.isLogin){
          return {'name':'login'};
        } 
        if(store.loginUser.userId!==routeUserId){
          return{'name':'boardList'}
        }
      },
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView,
      beforeEnter: ()=>{
        const store = useUserStore();
        if(!store.isLogin){
          return {'name':'login'};
        }
      },
      children: [
        {
          path: "",
          name: "boardList",
          component: BoardList
        },
        {
          path: "create",
          name: "boardCreate",
          component: BoardCreate
        },
        {
          path: ":id",
          name: "boardDetail",
          component: BoardDetail
        },
        
      ]
    },
  ],
  scrollBehavior(to, from, savedPosition) {
    // 항상 최상단으로 이동
    return { top: 0 };
  },
})

export default router
