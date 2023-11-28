<script setup lang="js">
import router from "./router";
import {useRoute} from "vue-router";
const route = useRoute()
/*router.isReady().then(() => {
  const student = JSON.parse(localStorage.getItem('student-user'))
  if (route.path !== '/login' && student?.name == null) {
    router.push('/login')
  }
})*/
</script>
<template>
  <RouterView />
</template>