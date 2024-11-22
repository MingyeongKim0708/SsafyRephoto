import { createApp } from 'vue'
import { createPinia } from 'pinia'

import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

// 부트스트랩 템플릿 스타일
import './assets/css/main.css'; // 템플릿 메인 CSS
import './assets/vendor/bootstrap/css/bootstrap.min.css'; // 부트스트랩 CSS

// 부트스트랩 템플릿 JS (필요 시)
// import './assets/js/main.js'; // 템플릿에서 제공한 JS

// import "bootstrap/dist/css/bootstrap.min.css"
// import "bootstrap/dist/js/bootstrap.min.js"
// import "bootstrap-icons/font/bootstrap-icons.css"


import AOS from 'aos'

import App from './App.vue'
import router from './router'

const app = createApp(App)

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)

app.mount('#app')
