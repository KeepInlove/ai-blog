import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import { createVuetify } from 'vuetify';
import 'vuetify/styles';

// import animated from "animate.css";
import "./assets/css/index.css";
import "./assets/css/iconfont.css";
import "./assets/css/markdown.css";
import "./assets/css/vue-social-share/client.css";
import config from "./utils/config";
// import Share from "vue-social-share";
import dayjs from "dayjs";
import { vueBaberrage } from "vue-baberrage";
import axios from "axios";
import VueAxios from "vue-axios";
import InfiniteLoading from "v3-infinite-loading";
import "v3-infinite-loading/lib/style.css"; // required if you're not going to override default slots
// import "highlight.js/styles/atom-one-dark.css";
// import VueImageSwipe from "vue-image-swipe";
import "vue-image-swipe/dist/vue-image-swipe.css";
// import Toast from "./components/toast/index";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import './assets/icons' // 导入全局icons


const app = createApp(App)


app.config.globalProperties.config = config;
app.config.productionTip = false;
// app.use(animated);
// app.use(Share);
app.use(vueBaberrage);
app.use(VueAxios, axios);
// app.use(VueImageSwipe);
// app.use(Toast);
app.use(ElementPlus);

const vuetify = createVuetify({})
app.use(vuetify)

app.component("infinite-loading", InfiniteLoading);




import ToastComponent from "@/components/toast/Toast.vue"; // 引入先前写好的vue
// 生成一个Vue的子类
const toastInstance = createApp(ToastComponent);
// 生成一个该子类的实例
// const instance = new ToastConstructor();
// 将这个实例挂载在我创建的div上
// 并将此div加入全局挂载点内部
toastInstance.mount(document.createElement("div"));
// document.body.appendChild(toastInstance.$el);

// 通过Vue的原型注册一个方法
// 让所有实例共享这个方法 <br>    // 其中的duration是持续时间
app.config.globalProperties.$toast = (options, duration = 2000) => {
  toastInstance.message = options.message;
  toastInstance.type = options.type;
  toastInstance.show = true;

  setTimeout(() => {
    toastInstance.show = false;
  }, duration);
};


import SvgIcon from '@/components/SvgIcon/index.vue'// svg component
// register globally
app.component('svg-icon', SvgIcon)

app.config.globalProperties.filter = {
  date: function(value) {
    return dayjs(value).format("YYYY-MM-DD");
  },
  year : function(value) {
    return dayjs(value).format("YYYY");
  },
  hour : function(value) {
    return dayjs(value).format("YYYY-MM-DD HH:mm:ss");
  },
  num : function(value) {
    if (value >= 1000) {
      return (value / 1000).toFixed(1) + "k";
    }
    return value;
  }
}


router.beforeEach((to, from, next) => {
  NProgress.start();
  if (to.meta.title) {
    document.title = to.meta.title;
  }
  next();
});

router.afterEach(() => {
  window.scrollTo({
    top: 0,
    behavior: "instant"
  });
  NProgress.done();
});

axios.interceptors.response.use(
  function(response) {
    console.log('response', response)
    switch (response.data.code) {
      case 50000:
        Vue.prototype.$toast({ type: "error", message: "系统异常" });
    }
    return response;
  },
  function(error) {
    return Promise.reject(error);
  }
);

app.use(router);
app.use(store);
app.mount('#app')
