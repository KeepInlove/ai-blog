import { createRouter, createWebHistory } from 'vue-router'

// Vue.use(VueRouter);

const routes = [
  // {
  //   path: "/login",
  //   name: "登录",
  //   hidden: true,
  //   component: () => import("../views/login/Login.vue")
  // }
  {
    path: "/login",
    name: "登录",
    hidden: true,
    component: () => import("../components/TranslateLogin/TranslateLogin.vue")
  }
];

const router = createRouter({
    history: createWebHistory(),
    base: "/solar/admin",
    routes,
})

export function resetRouter() {
    let routers = router.getRoutes()
    routers.map(it=>{
        if(!['login'].includes(it.name)){
            router.removeRoute(it.name)
        }
    })
}

export default router;
