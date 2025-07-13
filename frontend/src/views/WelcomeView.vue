<template>
  <div style="width:100vw; height:100vh;overflow:hidden;display: flex">
    <div style="flex: 1;">
      <el-image style="width: 100%;height: 100%"
                src="https://cdn.svipaigc.com/bizi/2023/11/194903-16946921437a6f.jpg"></el-image>
    </div>
    <div class="welcome-title">
      <div style="font-size: 30px;font-weight: bold">欢迎使用物流公司快递运营系统</div>
      <div style="margin-top: 10px">祝您使用愉快!</div>
    </div>
    <div style="width:500px;background-color:white;text-align: center;">
      <router-view/>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.welcome-title{
  position: absolute;
  bottom:30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>