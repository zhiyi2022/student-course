import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import '@/assets/css/global.css'

const app = createApp(App)

app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
})

router.beforeEach((to, _) => {
    const student = JSON.parse(localStorage.getItem('student-user'))
    if ((to.path !== '/login' && to.path !== '/register') && student?.username == null) {
        return {path: '/login'}      //当不在注册页也不在登录页，同时还没有登录的时候，直接跳转到登录页login
    }
})

router.isReady().then(() => {       // 路由加载后再挂载
    app.mount('#app')
})

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}