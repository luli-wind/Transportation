<template>
  <div class="welcome-container">
    <!-- 背景图区域 -->
    <div class="background-section">
      <div class="overlay"></div>
      <el-image class="background-image" fit="cover" :src="backgroundUrl"></el-image>
      <!-- 背景文字 -->
      <div class="welcome-title">
        <div class="welcome-title-main">欢迎使用物流公司快递运营系统</div>
        <div class="welcome-title-sub">祝您使用愉快!</div>
      </div>
    </div>

    <!-- 登录表单区域 -->
    <div class="form-section">
      <transition name="fade" mode="out-in">
        <router-view/>
      </transition>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const backgroundUrl = ref('https://cdn.svipaigc.com/bizi/2023/11/194903-16946921437a6f.jpg');
</script>

<style scoped>
.welcome-container {
  display: flex;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.background-section {
  flex: 1;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to right, rgba(0, 0, 0, 0.5), transparent);
  z-index: 1;
}

.background-image {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.welcome-title {
  position: relative;
  z-index: 2;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
  max-width: 70%;
  padding: 30px;
}

.welcome-title-main {
  font-size: 32px;
  font-weight: 800;
  line-height: 1.3;
  letter-spacing: 1px;
}

.welcome-title-sub {
  margin-top: 15px;
  font-size: 18px;
  opacity: 0.9;
}

.form-section {
  width: 520px;
  min-width: 520px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  box-shadow: -5px 0 15px rgba(0, 0, 0, 0.05);
  z-index: 10;
}

/* 路由过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>