<template>
  <div class="login-container">
    <!-- 顶部标题区域 -->
    <div class="login-header">
      <div class="system-name">物流运营系统</div>
      <h1 class="login-title">登录</h1>
      <div class="login-subtitle">请输入用户名和密码进行登录</div>
    </div>

    <!-- 登录表单 -->
    <el-form ref="loginForm" class="login-form" :model="form" @submit.prevent="handleLogin">
      <!-- 用户名输入 -->
      <el-form-item>
        <div class="input-label">用户名或邮箱</div>
        <el-input
            v-model="form.username"
            placeholder="请输入用户名或邮箱"
            size="large"
            @keyup.enter="handleLogin"
        >
          <template #prefix>
            <el-icon class="input-icon"><User /></el-icon>
          </template>
        </el-input>
      </el-form-item>

      <!-- 密码输入 -->
      <el-form-item>
        <div class="input-label">密码</div>
        <el-input
            v-model="form.password"
            placeholder="请输入密码"
            type="password"
            size="large"
            show-password
            @keyup.enter="handleLogin"
        >
          <template #prefix>
            <el-icon class="input-icon"><Lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>

      <!-- 选项区域 -->
      <div class="login-options">
        <el-checkbox v-model="rememberMe" size="large">
          记住我
        </el-checkbox>
        <el-link type="primary" :underline="false" class="forgot-password">
          忘记密码?
        </el-link>
      </div>

      <!-- 登录按钮 -->
      <el-form-item>
        <el-button
            class="login-button"
            type="primary"
            size="large"
            :loading="loading"
            @click="handleLogin"
        >
          登录系统
        </el-button>
      </el-form-item>
    </el-form>

    <!-- 新用户区域 -->
    <div class="new-user-section">
      <el-divider class="custom-divider">
        <span class="divider-text">没有账号?</span>
      </el-divider>

      <el-button class="register-button" type="info" plain size="large">
        创建新账号
      </el-button>
    </div>

    <!-- 底部信息 -->
    <div class="footer">
      <div class="copyright"> 物流运营系统 所有</div>
      <div class="version">V1.0.0</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { User, Lock } from '@element-plus/icons-vue';
import {ElMessage} from "element-plus";
import {post,get} from '@/utils/request.js'
import router from "@/router/index.js";


// 表单数据
const form = ref({
  username: '',
  password: '',

});

// 其他状态
const rememberMe =ref(false)
const loading = ref(false);

// 登录处理函数
const handleLogin = () => {
  if(!form.value.username || !form.value.password) {
    ElMessage.warning("请填写用户名和密码")
  }else {
    loading.value = true;//设置提交状态，防止重复提交
    post('/api/auth/login',{
      username: form.value.username,
      password: form.value.password,
      rememberMe: rememberMe.value,
        },
        ()=>{
      ElMessage.success("登陆成功,欢迎使用!");
      router.push('/index');
    },
        (message, status) => { // 失败回调
          ElMessage.warning(`${message} (状态码: ${status})`);
          loading.value = false; // 显式重置 loading
        },
        (errorMsg) => { // 网络/配置错误回调
          ElMessage.error(errorMsg);
          loading.value = false; // 显式重置 loading
        })
  }

};
</script>

<style scoped>
.login-container {
  width: 400px;
  padding: 0 20px;
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.system-name {
  font-size: 24px;
  color: #409eff;
  font-weight: 600;
  letter-spacing: 1px;
  margin-bottom: 5px;
}

.login-title {
  font-size: 24px;
  color: #333;
  font-weight: 700;
  margin: 10px 0;
}

.login-subtitle {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}

.login-form {
  margin-top: 20px;
}

.input-label {
  font-size: 14px;
  color: #606266;
  font-weight: 500;
  margin-bottom: 8px;

}

.input-icon {
  color: #c0c4cc;
  margin-right: 8px;
}

.el-input :deep(.el-input__wrapper) {
  border-radius: 8px;
  height: 48px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 15px 0 25px;
}

.forgot-password {
  font-size: 14px;
  font-weight: 500;
}

.login-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 1px;
  border-radius: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.4);
}

.new-user-section {
  margin-top: 40px;
}

.custom-divider {
  margin: 25px 0;
}

.divider-text {
  color: #999;
  font-size: 14px;
  padding: 0 12px;
}

.register-button {
  width: 100%;
  height: 46px;
  border-radius: 8px;
  font-weight: 500;
}

.footer {
  margin-top: 40px;
  text-align: center;
  font-size: 12px;
  color: #999;
  padding-bottom: 20px;
}

.copyright {
  margin-bottom: 8px;
}

.version {
  color: #bbb;
}

/* 增强输入框的阴影效果 */
:deep(.el-input .el-input__wrapper) {
  border-radius: 8px;
  height: 48px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15), /* 增强扩散阴影 */
  0 1px 3px rgba(0, 0, 0, 0.1) inset; /* 添加内阴影增强立体感 */
  transition: all 0.3s ease;
  border: 1px solid #e6e6e6; /* 添加细微边框提升识别度 */
}

/* 输入框聚焦时的高亮效果 */
:deep(.el-input.is-focus .el-input__wrapper) {
  box-shadow: 0 2px 12px rgba(64, 158, 255, 0.3), /* 增强蓝色扩散效果 */
  0 1px 3px rgba(64, 158, 255, 0.1) inset; /* 内阴影跟随变化 */
  border-color: #409eff; /* 边框颜色变为主题蓝色 */
}

/* 悬停状态增强效果 */
:deep(.el-input .el-input__wrapper:hover) {
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
  border-color: #dcdfe6;
}

/* 图标颜色增强 */
.input-icon {
  color: #909399; /* 加深图标颜色 */
  font-size: 16px; /* 稍大图标 */
  margin-right: 8px;
}

/* 输入框聚焦时图标颜色变化 */
:deep(.el-input.is-focus .input-icon) {
  color: #409eff;
}

/* 增强标签文本效果 */
.input-label {
  font-size: 14px;
  color: #303133; /* 加深标签文字颜色 */
  font-weight: 600; /* 加粗标签 */
  margin-bottom: 8px;
  display: flex;
  align-items: center;
}

/* 添加标签小图标增强识别度 */
.input-label::before {
  content: "•";
  color: #409eff;
  margin-right: 6px;
  font-size: 16px;
}
</style>