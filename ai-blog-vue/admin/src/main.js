import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import "./assets/css/index.css";
import "./assets/css/iconfont.css";
import config from "./utils/config";
import axios from "axios";
import VueAxios from "vue-axios";
import ECharts from "vue-echarts";
import "echarts/lib/chart/line";
import "echarts/lib/chart/pie";
import "echarts/lib/chart/bar";
import "echarts/lib/chart/map";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
// import mavonEditor from "mavon-editor";
// import "mavon-editor/dist/css/index.css";
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
// highlightjs
import hljs from 'highlight.js';

import NProgress from "nprogress";
import "nprogress/nprogress.css";
// import VueCalendarHeatmap from "vue-calendar-heatmap";
import dayjs from "dayjs";
import TagCloud from 'vue-tag-cloud';

import './assets/icons' // 导入全局icons


VMdEditor.use(githubTheme, {
  Hljs: hljs,
});


const app = createApp(App)


app.use(VMdEditor);
app.use(TagCloud);
// app.use(VueCalendarHeatmap);
app.use(VueAxios, axios);
app.use(ElementPlus);

app.component("v-chart", ECharts);
app.config.productionTip = false;
app.config.globalProperties.$moment = dayjs;
app.config.globalProperties.config = config;

app.config.globalProperties.$filters = {

  date: (value, formatStr = "YYYY-MM-DD")=> {
    return dayjs(value).format(formatStr);
  },
  dateTime:(value, formatStr = "YYYY-MM-DD HH:mm:ss")=> {
    return dayjs(value).format(formatStr);
  }
}


NProgress.configure({
  easing: "ease", // 动画方式
  speed: 500, // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3 // 初始化时的最小百分比
});

router.beforeEach((to, from, next) => {
  NProgress.start();
  if (to.path === "/login") {
    next();
  } else if (!store.state.userId) {
    // console.log('login')
    next({ path: "/login" });
  } else {
    next();
  }
});

router.afterEach(() => {
  NProgress.done();
});

// 响应拦截器
axios.interceptors.response.use(
  function(response) {
    switch (response.data.code) {
      case 40001:
        app.config.globalProperties.$message({
          type: "error",
          message: response.data.message
        });
        router.push({ path: "/login" });
        break;
      case 50000:
        app.config.globalProperties.$message({
          type: "error",
          message: response.data.message
        });
        break;
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
