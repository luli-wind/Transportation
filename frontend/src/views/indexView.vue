<script setup>
import {get} from '@/utils/request.js'
import {ElMessage} from "element-plus";
import router from "@/router/index.js";
//登出函数
const logout =() =>{
  get('/api/auth/logout',null,
      ()=> {
        ElMessage.success("退出登录成功!");
        router.push('/');
      })
}
</script>

<template>
  <div>
    欢迎使用
  </div>
  <div>
    <el-button type="primary" plain @click="logout">退出登录</el-button>
  </div>
</template>

<style scoped>

</style>