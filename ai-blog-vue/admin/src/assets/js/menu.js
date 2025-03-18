import Layout from "@/layout/index.vue";
import router from "../../router";
import store from "../../store";
import axios from "@/utils/request.js";
import {ElMessage} from "element-plus";
import service from "@/utils/request.js";

const modules = import.meta.glob('./../../views/**/*.vue')

export function generaMenu() {
  const token=localStorage.getItem("token")
  console.log("Token from localStorage:", token);
  // 查询用户菜单
  return new Promise((resolve, reject) => {
    service({
      headers: {
        Authorization: "Bearer " + token
      },
      url:"/admin/user/menus",
      method:"get"
    }).then(({ data }) => {
      if (data.flag) {
        var userMenuList = data.data;
        userMenuList.forEach(item => {
          if (item.icon != null) {
            item.icon = "iconfont " + item.icon;
          }
          if (item.component == "Layout") {
            item.component = Layout;
          }
          if (item.children && item.children.length > 0) {
            item.children.forEach(route => {
              route.icon = "iconfont " + route.icon;
              route.component = loadView(route.component);
            });
          }
          // 添加菜单到路由
          router.addRoute(item);
        });
        // 添加侧边栏菜单
        store.commit("saveUserMenuList", userMenuList);
        console.log(router.getRoutes())
        resolve()
      } else {
        ElMessage.error(data.message);
        router.push({ path: "/login" });
      }
    });
  })
}

export const loadView = view => {
  let res;
  for (const path in modules) {
    const dir ="/" + path.split('views/')[1];
    // const dir = path.split('views/')[1].split('.vue')[0];
    if (dir === view) {
      console.log('匹配', view,dir)
      res = () => modules[path]();
    }
  }
  return res;
};
