<template>
  <div style="text-align: center;margin: 30px">
    <div style="margin-top: 230px">
      <div style="font-size: 25px;color:black;font-weight: bold">登录</div>
      <div style="font-size: 14px;color: gray">请输入用户名和密码进行登录</div>
    </div>
    <div style="margin-top: 50px;" >
      <el-input type="text" placeholder="请输入用户名或邮箱">
        <template #prefix>
          <el-icon><User /></el-icon>
        </template>
      </el-input>
      <el-input type="password" style="margin-top: 20px" placeholder="请输入密码">
        <template #prefix>
          <el-icon><Lock /></el-icon>
        </template>
      </el-input>
    </div>
    <div style="margin-top: 5px">
      <el-row>
        <el-col :span="12" style="text-align: left;">
          <el-checkbox v-model="checked1" label="记住我" size="large" />
        </el-col>
        <el-col :span="12" style="text-align: right;">
          <el-link>忘记密码</el-link>
        </el-col>
      </el-row>
    </div>
    <div style="margin-top: 20px">
      <el-button type="primary" style="width: 120px">登录</el-button>
    </div>
    <el-divider>
      <span style="color: gray">没有账号</span>
    </el-divider>
    <div>
      <el-button type="info" plain>注册账号</el-button>
    </div>
  </div>
</template>


<script setup>
</script>



<style scoped>
</style>