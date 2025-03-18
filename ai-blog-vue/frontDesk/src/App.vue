<template>
  <v-app id="app">
    <!-- 导航栏 -->
    <TopNavBar></TopNavBar>
    <!-- 侧边导航栏 -->
    <SideNavBar></SideNavBar>
    <!-- 内容 -->
    <v-content>
      <router-view :key="$route.fullPath" />
    </v-content>
    <!-- 页脚 -->
    <Footer></Footer>
    <!-- 返回顶部 -->
    <BackTop></BackTop>
    <!-- 搜索模态框 -->
    <searchModel></searchModel>
    <!-- 登录模态框 -->
    <LoginModel></LoginModel>
    <!-- 注册模态框 -->
    <RegisterModel></RegisterModel>
    <!-- 忘记密码模态框 -->
    <ForgetModel></ForgetModel>
    <!-- 绑定邮箱模态框 -->
    <EmailModel></EmailModel>
    <!-- 音乐播放器 -->
<!--    <Player v-if="blogInfo.websiteConfig.isMusicPlayer == 1 && !isMobile" />-->
    <!-- 聊天室 -->
    <ChatRoom v-if="blogInfo.websiteConfig.isChatRoom === 1"></ChatRoom>
  </v-app>
</template>

<script>
import TopNavBar from "@/components/layout/TopNavBar.vue";
import SideNavBar from "./components/layout/SideNavBar.vue";
import Footer from "@/components/layout/Footer.vue";
import BackTop from "@/components/BackTop.vue";
import searchModel from "@/components/model/SearchModel.vue";
import LoginModel from "@/components/model/LoginModel.vue";
import RegisterModel from "@/components/model/RegisterModel.vue";
import ForgetModel from "@/components/model/ForgetModel.vue";
import EmailModel from "@/components/model/EmailModel.vue";
// import Player from "@/components/zw-player/player.vue";
// import store from '@/store'
import ChatRoom from "@/components/ChatRoom.vue";
import {useStore} from "vuex";
export default {
  components: {
    TopNavBar,
    // Player,
    SideNavBar,
    Footer,
    BackTop,
    searchModel,
    LoginModel,
    RegisterModel,
    ForgetModel,
    EmailModel,
    ChatRoom
  },
  setup() {
    const store = useStore();
    return {
      store
    };
  },
  created() {
    console.log('created')
  },
  mounted() {
    console.log('mounted')
    // 获取博客信息
    this.getBlogInfo();
    // 上传访客信息
    this.axios.post("/api/report");
  },
  methods: {
    async getBlogInfo() {
      await this.axios.get("/api/").then(res => {
        this.$store.commit("checkBlogInfo", res.data.data);
      });
    }
  },
  computed: {
    blogInfo() {
      console.log( 'aaaaaaaaaaaaaaa',this.store.state)
      return {
        websiteConfig:{}
      }
    },
    isMobile() {
      const flag = navigator.userAgent.match(
        /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
      );
      return flag;
    }
  }
};
</script>
