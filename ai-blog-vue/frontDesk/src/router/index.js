import { createRouter, createWebHistory } from 'vue-router'



const routes = [
  {
    path: "/",
    component: resolve => import("@/views/home/Home.vue")
  },
  {
    path: "/articles/:articleId",
    component: resolve => import("../views/article/Article.vue")
  },
  {
    path: "/archives",
    component: resolve => import("../views/archive/Archive.vue"),
    meta: {
      title: "斜阳技术博客-归档"
    }
  },
  {
    path: "/albums",
    component: resolve => import("../views/album/Album.vue"),
    meta: {
      title: "斜阳技术博客-相册"
    }
  },
  {
    path: "/albums/:albumId",
    component: resolve => import("../views/album/Photo.vue")
  },
  {
    path: "/tags",
    component: resolve => import("../views/tag/Tag.vue"),
    meta: {
      title: "斜阳技术博客-标签"
    }
  },
  {
    path: "/categories",
    component: resolve => import("../views/category/Category.vue"),
    meta: {
      title: "斜阳技术博客-分类"
    }
  },
  {
    path: "/categories/:categoryId",
    component: resolve => import("../views/article/ArticleList.vue")
  },
  {
    path: "/tags/:tagId",
    component: resolve => import("../views/article/ArticleList.vue")
  },
  {
    path: "/links",
    component: resolve => import("../views/link/Link.vue"),
    meta: {
      title: "斜阳技术博客-友链"
    }
  },
  {
    path: "/siteNavigation",
    component: resolve => import("../views/siteNavigation/siteNavigation.vue"),
    meta: {
      title: "斜阳技术博客-网站导航"
    }
  },
  {
    path: "/message",
    component: resolve => import("../views/message/Message.vue"),
    meta: {
      title: "斜阳技术博客-留言板"
    }
  },
  {
    path: "/user",
    component: resolve => import("../views/user/User.vue"),
    meta: {
      title: "斜阳技术博客-个人中心"
    }
  },
  {
    path: "/oauth/login/qq",
    component: resolve => import("../components/OauthLogin.vue")
  },
  {
    path: "/oauth/login/weibo",
    component: resolve => import("../components/OauthLogin.vue")
  }
];

const router = createRouter({
  history: createWebHistory("/solar/web"),
  routes
});

export default router;
