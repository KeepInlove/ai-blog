<template>
  <div>
    <!-- 内容 -->
    <v-row class="article-container">
      <!-- 侧边功能 -->
      <v-col md="3" cols="12" class="d-md-block d-none">
        <div style="position: sticky;top: 20px;">
          <!-- 文章目录 -->
          <v-card class="right-container">
            <div class="right-title">
              <i class="iconfont iconhanbao" style="font-size:16.8px" />
              <span style="margin-left:10px">目录</span>
            </div>
            <div id="toc" />
          </v-card>
          <!-- 最新文章 -->
          <v-card class="right-container" style="margin-top:20px">
            <div class="right-title">
              <i class="iconfont icongengxinshijian" style="font-size:16.8px" />
              <span style="margin-left:10px">最新文章</span>
            </div>
            <div class="article-list">
              <div
                      class="article-item"
                      v-for="item of article.newestArticleList"
                      :key="item.id"
              >
                <router-link :to="'/articles/' + item.id" class="content-cover">
                  <img :src="item.articleCover" />
                </router-link>
                <div class="content">
                  <div class="content-title">
                    <router-link :to="'/articles/' + item.id">
                      {{ item.articleTitle }}
                    </router-link>
                  </div>
                  <div class="content-time">{{ item.createTime | date }}</div>
                </div>
              </div>
            </div>
          </v-card>
        </div>
      </v-col>
      <v-col md="9" cols="12">
        <v-card class="article-wrapper">
          <!-- 文章标题 -->
          <div class="article-title">{{ article.articleTitle }}</div>
          <div class="article-info">
            <div class="first-line">
              <!-- 发表时间 -->
              <span>
              <i class="iconfont iconrili" />
              发表于 {{ article.createTime | date }}
            </span>
              <span class="separator">|</span>
              <!-- 发表时间 -->
              <span>
              <i class="iconfont icongengxinshijian" />
              更新于
              <template v-if="article.updateTime">
                {{ article.updateTime | date }}
              </template>
              <template v-else>
                {{ article.createTime | date }}
              </template>
            </span>
              <span class="separator">|</span>
              <!-- 字数统计 -->
              <span>
              <i class="iconfont iconzishu" />
              字数统计: {{ wordNum | num }}
            </span>
              <span class="separator">|</span>
              <!-- 阅读时长 -->
              <span>
              <i class="iconfont iconshijian" />
              阅读时长: {{ readTime }}
            </span>
              <span class="separator">|</span>
              <!-- 阅读量 -->
              <span>
              <i class="iconfont iconliulan" /> 阅读量: {{ article.viewsCount }}
            </span>
              <span class="separator">|</span>
              <!-- 文章分类 -->
              <span class="article-category">
              <router-link :to="'/categories/' + article.categoryId">
                {{ article.categoryName }}
              </router-link>
              </span>
              <span class="separator">|</span>
              <!-- 评论量 -->
              <span>
              <i class="iconfont iconpinglunzu1" />评论数:
              <template v-if="count">{{ count }}</template>
              <template v-else>0</template>
            </span>
          </div>
          </div>
          <br>
          <br>
          <article
            id="write"
            class="article-content markdown-body"
            v-html="article.articleContent"
            ref="article"
          />
          <!-- 版权声明 -->
          <div class="aritcle-copyright">
            <div>
              <span>文章作者：</span>
              <router-link to="/">
                {{ blogInfo.websiteConfig.websiteAuthor }}
              </router-link>
            </div>
            <div>
              <span>文章链接：</span>
              <a :href="articleHref" target="_blank">{{ articleHref }} </a>
            </div>
            <div>
              <span>版权声明：</span>本博客所有文章除特别声明外，均采用
              <a
                href="https://creativecommons.org/licenses/by-nc-sa/4.0/"
                target="_blank"
              >
                CC BY-NC-SA 4.0
              </a>
              许可协议。转载请注明文章出处。
              <p><span style="color: #00a1d6">注意：</span>本站用户发帖仅代表本站用户个人观点，并不代表本站赞同其观点和对其真实性负责。</p>
              <span>本站资源多为网络收集，如涉及版权问题请及时与站长联系，我们会在第一时间内删除资源。</span>
            </div>
          </div>
          <!-- 转发 -->
          <div class="article-operation">
            <div class="tag-container">
              <router-link
                v-for="item of article.tagDTOList"
                :key="item.id"
                :to="'/tags/' + item.id"
              >
                {{ item.tagName }}
              </router-link>
            </div>
            <share style="margin-left:auto" :config="config" />
          </div>
          <!-- 点赞打赏等 -->
          <div class="article-reward">
            <!-- 点赞按钮 -->
            <a :class="isLike" @click="like">
              <v-icon size="14" color="#fff">mdi-thumb-up</v-icon> 点赞
              <span v-show="article.likeCount > 0">
                {{article.likeCount}}
              </span>
            </a>

            <!-- 点赞按钮 -->
            <a class="reward-btn" @click="dialogFormVisible = true">
              <i class="iconfont iconerweima" /> 购买
            </a>
          </div>
          <div class="pagination-post">
            <!-- 上一篇 -->
            <div
              :class="isFull(article.lastArticle.id)"
              v-if="article.lastArticle.id"
            >
              <router-link :to="'/articles/' + article.lastArticle.id">
                <img
                  class="post-cover"
                  :src="article.lastArticle.articleCover"
                />
                <div class="post-info">
                  <div class="label">上一篇</div>
                  <div class="post-title">
                    {{ article.lastArticle.articleTitle }}
                  </div>
                </div>
              </router-link>
            </div>
            <!-- 下一篇 -->
            <div
              :class="isFull(article.nextArticle.id)"
              v-if="article.nextArticle.id"
            >
              <router-link :to="'/articles/' + article.nextArticle.id">
                <img
                  class="post-cover"
                  :src="article.nextArticle.articleCover"
                />
                <div class="post-info" style="text-align: right">
                  <div class="label">下一篇</div>
                  <div class="post-title">
                    {{ article.nextArticle.articleTitle }}
                  </div>
                </div>
              </router-link>
            </div>
          </div>
          <!-- 推荐文章 -->
          <div
            class="recommend-container"
            v-if="article.recommendArticleList.length"
          >
            <div class="recommend-title">
              <v-icon size="20" color="#4c4948">mdi-thumb-up</v-icon> 相关推荐
            </div>
            <div class="recommend-list">
              <div
                class="recommend-item"
                v-for="item of article.recommendArticleList"
                :key="item.id"
              >
                <router-link :to="'/articles/' + item.id">
                  <img class="recommend-cover" :src="item.articleCover" />
                  <div class="recommend-info">
                    <div class="recommend-date">
                      <i class="iconfont iconrili" />
                      {{ item.createTime | date }}
                    </div>
                    <div>{{ item.articleTitle }}</div>
                  </div>
                </router-link>
              </div>
            </div>
          </div>
          <!-- 分割线 -->
          <hr />
          <!-- 评论 -->
          <comment
            :commentList="commentList"
            :count="count"
            @reloadComment="listComment"
          />
        </v-card>
      </v-col>
    </v-row>

    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="buyInfo">
        <el-form-item label="邮箱" :label-width="120">
          <el-input v-model="buyInfo.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <canvas id="payCode" ref="payCode" title="扫描二维码"></canvas>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="buy">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
  import Clipboard from "clipboard";
  import Comment from "../../components/Comment";
  import tocbot from "tocbot";
  import qrCode from "qrcode";
  import {addBuy, addLike, getArticle, getComments} from "@/api/article.js";

  export default {
  components: {
    Comment
  },
  created() {
    this.getArticle();
    this.listComment();
  },
  destroyed() {
    this.clipboard.destroy();
    tocbot.destroy();
  },
  data: function() {
    return {
      dialogFormVisible:false,
      buyInfo:{
        email:''
      },
      config: {
        sites: ["qzone", "wechat", "weibo", "qq"]
      },
      imgList: [],
      article: {
        nextArticle: {
          id: 0,
          articleCover: ""
        },
        lastArticle: {
          id: 0,
          articleCover: ""
        },
        recommendArticleList: [],
        newestArticleList: []
      },
      commentList: [],
      count: 0,
      wordNum: "",
      readTime: "",
      articleHref: window.location.href,
      clipboard: null
    };
  },
  methods: {
    getArticle() {
      const that = this;
      //查询文章
      getArticle(this.$route.path).then(res => {
        document.title = res.data.articleTitle;
        //将markdown转换为Html
        this.markdownToHtml(res.data);
        this.$nextTick(() => {
          // 统计文章字数
          this.wordNum = this.deleteHTMLTag(this.article.articleContent).length;
          // 计算阅读时间
          this.readTime = Math.round(this.wordNum / 400) + "分钟";
          // 添加代码复制功能
          this.clipboard = new Clipboard(".copy-btn");
          this.clipboard.on("success", () => {
            this.$toast({ type: "success", message: "复制成功" });
          });
          // 添加文章生成目录功能
          let nodes = this.$refs.article.children;
          if (nodes.length) {
            for (let i = 0; i < nodes.length; i++) {
              let node = nodes[i];
              let reg = /^H[1-4]{1}$/;
              if (reg.exec(node.tagName)) {
                node.id = i;
              }
            }
          }
          tocbot.init({
            tocSelector: "#toc", //要把目录添加元素位置，支持选择器
            contentSelector: ".article-content", //获取html的元素
            headingSelector: "h1, h2, h3", //要显示的id的目录
            hasInnerContainers: true,
            onClick: function(e) {
              e.preventDefault();
            }
          });
          // 添加图片预览功能
          const imgList = this.$refs.article.getElementsByTagName("img");
          for (var i = 0; i < imgList.length; i++) {
            this.imgList.push(imgList[i].src);
            imgList[i].addEventListener("click", function(e) {
              that.previewImg(e.target.currentSrc);
            });
          }
        });
      });
    },
    listComment() {
      const path = this.$route.path;
      const arr = path.split("/");
      const articleId = arr[arr.length - 1];
      getComments({ current: 1, articleId: articleId }).then(res => {
        this.commentList = res.data.recordList;
        this.count = res.data.count;
      });
    },
    like() {
      // 判断是否登录
      if (!this.$store.state.userId) {
        this.$store.state.loginFlag = true;
        return false;
      }
      //发送点赞请求
      addLike(this.article.id).then(res => {
        if (res.flag) {
          //判断是否点赞
          if (this.$store.state.articleLikeSet.indexOf(this.article.id) !== -1) {
            this.$set(this.article, "likeCount", this.article.likeCount - 1);
          } else {
            this.$set(this.article, "likeCount", this.article.likeCount + 1);
          }
          this.$store.commit("articleLike", this.article.id);
        }
      });
    },
    buy() {
      //发送点赞请求
      addBuy(this.article.id, this.buyInfo).then(res => {
        console.log(res);
        let opts = {
          errorCorrectLevel: "H",// 容错级别
          type: "img/png",// 图片类型
          quality: 0.3,//二维码质量
          margin: 5,//
          width: 200,
          height: 200,
          text: res.data
        }
        qrCode.toCanvas(this.$refs.payCode, res.data, opts, function (err) {
          if (err) {
            alert("生成失败")
          }
        })

      });
    },
    markdownToHtml(article) {
      const MarkdownIt = require("markdown-it");
      const hljs = require("highlight.js");
      const md = new MarkdownIt({
        html: true,
        linkify: true,
        typographer: true,
        highlight: function(str, lang) {
          // 当前时间加随机数生成唯一的id标识
          var d = new Date().getTime();
          if (
                  window.performance &&
                  typeof window.performance.now === "function"
          ) {
            d += performance.now();
          }
          const codeIndex = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(
                  /[xy]/g,
                  function(c) {
                    var r = (d + Math.random() * 16) % 16 | 0;
                    d = Math.floor(d / 16);
                    return (c == "x" ? r : (r & 0x3) | 0x8).toString(16);
                  }
          );
          // 复制功能主要使用的是 clipboard.js
          let html = `<button class="copy-btn iconfont iconfuzhi" type="button" data-clipboard-action="copy" data-clipboard-target="#copy${codeIndex}"></button>`;
          const linesLength = str.split(/\n/).length - 1;
          // 生成行号
          let linesNum = '<span aria-hidden="true" class="line-numbers-rows">';
          for (let index = 0; index < linesLength; index++) {
            linesNum = linesNum + "<span></span>";
          }
          linesNum += "</span>";
          if (lang && hljs.getLanguage(lang)) {
            // highlight.js 高亮代码
            const preCode = hljs.highlight(lang, str, true).value;
            html = html + preCode;
            if (linesLength) {
              html += '<b class="name">' + lang + "</b>";
            }
            // 将代码包裹在 textarea 中，由于防止textarea渲染出现问题，这里将 "<" 用 "<" 代替，不影响复制功能
            return `<pre class="hljs"><code>${html}</code>${linesNum}</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy${codeIndex}">${str.replace(
                    /<\/textarea>/g,
                    "</textarea>"
            )}</textarea>`;
          }
        }
      });
      // 将markdown替换为html标签
      article.articleContent = md.render(article.articleContent);
      this.article = article;
    },
    previewImg(img) {
      this.$imagePreview({
        images: this.imgList,
        index: this.imgList.indexOf(img)
      });
    },
    deleteHTMLTag(content) {
      return content
        .replace(/<\/?[^>]*>/g, "")
        .replace(/[|]*\n/, "")
        .replace(/&npsp;/gi, "");
    }
  },
  computed: {
    blogInfo() {
      return this.$store.state.blogInfo;
    },
    articleCover() {
      return (
        "background: url(" +
        this.article.articleCover +
        ") center center / cover no-repeat"
      );
    },
    isLike() {
      var articleLikeSet = this.$store.state.articleLikeSet;
      return articleLikeSet.indexOf(this.article.id) !== -1
        ? "like-btn-active"
        : "like-btn";
    },
    isFull() {
      return function(id) {
        return id ? "post full" : "post";
      };
    }
  }
};
</script>

<style scoped>
.banner:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 100%;
  background-color: rgba(255, 255, 255, -4);
}
.article-info i {
  font-size: 14px;
}
.article-info {
  font-size: 14px;
  line-height: 1.9;
  display: inline-block;
}
@media (min-width: 760px) {
  .banner {
    color: black !important;
  }
  .article-info span {
    font-size: 95%;
  }
  .article-title {
    font-size: 35px;
    margin: 20px 0 8px;
    display: flex;
    justify-content: center;
  }
  .pagination-post {
    display: flex;
  }
  .post {
    width: 50%;
  }
  .recommend-item {
    position: relative;
    display: inline-block;
    overflow: hidden;
    margin: 3px;
    width: calc(33.333% - 6px);
    height: 200px;
    background: #000;
    vertical-align: bottom;
  }
}

@media (max-width: 759px) {
  .banner {
    color: #eee !important;
    height: 360px;
  }
  .article-info span {
    font-size: 90%;
  }
  .separator:first-child {
    display: none;
  }
  .article-title {
    font-size: 1.5rem;
    margin-bottom: 0.4rem;
    display: flex;
    justify-content: center;

  }
  .post {
    width: 100%;
  }
  .pagination-post {
    display: block;
  }
  .recommend-item {
    position: relative;
    display: inline-block;
    overflow: hidden;
    margin: 3px auto;
    width: calc(100% - 4px);
    height: 150px;
    background: #000;
    vertical-align: bottom;
  }
}
.article-content {
  word-break: break-word;
  font-size: 14px;
  line-height: 2;
  padding: 0 0.9rem;
}
.article-operation {
  display: flex;
  align-items: center;
}
.article-category a {
  color: royalblue !important;
}
.tag-container a {
  display: inline-block;
  margin: 0.5rem 0.5rem 0.5rem 0;
  padding: 0 0.75rem;
  width: fit-content;
  border: 1px solid #49b1f5;
  border-radius: 1rem;
  color: #49b1f5 !important;
  font-size: 12px;
  line-height: 2;
}
.tag-container a:hover {
  color: #fff !important;
  background: #49b1f5;
  transition: all 0.5s;
}
.aritcle-copyright {
  position: relative;
  margin-top: 40px;
  margin-bottom: 10px;
  font-size: 0.875rem;
  line-height: 2;
  padding: 0.625rem 1rem;
  border: 1px solid #eee;
}
.aritcle-copyright span {
  color: #49b1f5;
  font-weight: bold;
}
.aritcle-copyright a {
  text-decoration: underline !important;
  color: #99a9bf !important;
}
.aritcle-copyright:before {
  position: absolute;
  top: 0.7rem;
  right: 0.7rem;
  width: 1rem;
  height: 1rem;
  border-radius: 1rem;
  background: #49b1f5;
  content: "";
}
.aritcle-copyright:after {
  position: absolute;
  top: 0.95rem;
  right: 0.95rem;
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 0.5em;
  background: #fff;
  content: "";
}
.article-reward {
  margin-top: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;
}
.reward-btn {
  position: relative;
  display: inline-block;
  width: 100px;
  background: #49b1f5;
  margin: 0 1rem;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.reward-btn:hover .reward-main {
  display: block;
}
.reward-main {
  display: none;
  position: absolute;
  bottom: 40px;
  left: 0;
  margin: 0;
  padding: 0 0 15px;
  width: 100%;
}
.reward-all {
  display: inline-block;
  margin: 0 0 0 -110px;
  padding: 20px 10px 8px !important;
  width: 320px;
  border-radius: 4px;
  background: #f5f5f5;
}
.reward-all:before {
  position: absolute;
  bottom: -10px;
  left: 0;
  width: 100%;
  height: 20px;
  content: "";
}
.reward-all:after {
  content: "";
  position: absolute;
  right: 0;
  bottom: 2px;
  left: 0;
  margin: 0 auto;
  width: 0;
  height: 0;
  border-top: 13px solid #f5f5f5;
  border-right: 13px solid transparent;
  border-left: 13px solid transparent;
}
.reward-item {
  display: inline-block;
  padding: 0 8px;
  list-style-type: none;
}
.reward-img {
  width: 130px;
  height: 130px;
  display: block;
}
.reward-desc {
  margin: -5px 0;
  color: #858585;
  text-align: center;
}
.like-btn {
  display: inline-block;
  width: 100px;
  background: #969696;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.like-btn-active {
  display: inline-block;
  width: 100px;
  background: #ec7259;
  color: #fff !important;
  text-align: center;
  line-height: 36px;
  font-size: 0.875rem;
}
.pagination-post {
  margin-top: 40px;
  overflow: hidden;
  width: 100%;
  background: #000;
}
.post {
  position: relative;
  height: 150px;
  overflow: hidden;
}
.post-info {
  position: absolute;
  top: 50%;
  padding: 20px 40px;
  width: 100%;
  transform: translate(0, -50%);
  line-height: 2;
  font-size: 14px;
}
.post-cover {
  position: absolute;
  width: 100%;
  height: 100%;
  opacity: 0.4;
  transition: all 0.6s;
  object-fit: cover;
}
.post a {
  position: relative;
  display: block;
  overflow: hidden;
  height: 150px;
}
.post:hover .post-cover {
  opacity: 0.8;
  transform: scale(1.1);
}
.label {
  font-size: 90%;
  color: #eee;
}
.post-title {
  font-weight: 500;
  color: #fff;
}
hr {
  position: relative;
  margin: 40px auto;
  border: 2px dashed #d2ebfd;
  width: calc(100% - 4px);
}
.full {
  width: 100% !important;
}
.right-container {
  padding: 20px 24px;
  font-size: 14px;
}
.right-title {
  display: flex;
  align-items: center;
  line-height: 2;
  font-size: 16.8px;
  margin-bottom: 6px;
}
.right-title i {
  font-weight: bold;
}
.recommend-container {
  margin-top: 40px;
}
.recommend-title {
  font-size: 20px;
  line-height: 2;
  font-weight: bold;
  margin-bottom: 5px;
}
.recommend-cover {
  width: 100%;
  height: 100%;
  opacity: 0.4;
  transition: all 0.6s;
  object-fit: cover;
}
.recommend-info {
  line-height: 2;
  color: #fff;
  position: absolute;
  top: 50%;
  padding: 0 20px;
  width: 100%;
  transform: translate(0, -50%);
  text-align: center;
  font-size: 14px;
}
.recommend-date {
  font-size: 90%;
}
.recommend-item:hover .recommend-cover {
  opacity: 0.8;
  transform: scale(1.1);
}
.article-item {
  display: flex;
  align-items: center;
  padding: 6px 0;
}
.article-item:first-child {
  padding-top: 0;
}
.article-item:last-child {
  padding-bottom: 0;
}
.article-item:not(:last-child) {
  border-bottom: 1px dashed #f5f5f5;
}
.article-item img {
  width: 100%;
  height: 100%;
  transition: all 0.6s;
  object-fit: cover;
}
.article-item img:hover {
  transform: scale(1.1);
}
.content {
  flex: 1;
  padding-left: 10px;
  word-break: break-all;
  display: -webkit-box;
  overflow: hidden;
  -webkit-box-orient: vertical;
}
.content-cover {
  width: 58.8px;
  height: 58.8px;
  overflow: hidden;
}
.content-title a {
  transition: all 0.2s;
  font-size: 95%;
}
.content-title a:hover {
  color: #2ba1d1;
}
.content-time {
  color: #858585;
  font-size: 85%;
  line-height: 2;
}
</style>

<style lang="scss">
  pre.hljs {
    padding: 12px 2px 12px 40px !important;
    border-radius: 5px !important;
    position: relative;
    font-size: 14px !important;
    line-height: 22px !important;
    overflow: hidden !important;
    &:hover .copy-btn {
      display: flex;
      justify-content: center;
      align-items: center;
    }
    code {
      display: block !important;
      margin: 0 10px !important;
      overflow-x: auto !important;
      &::-webkit-scrollbar {
        z-index: 11;
        width: 6px;
      }
      &::-webkit-scrollbar:horizontal {
        height: 6px;
      }
      &::-webkit-scrollbar-thumb {
        border-radius: 5px;
        width: 6px;
        background: #666;
      }
      &::-webkit-scrollbar-corner,
      &::-webkit-scrollbar-track {
        background: #1e1e1e;
      }
      &::-webkit-scrollbar-track-piece {
        background: #1e1e1e;
        width: 6px;
      }
    }
    .line-numbers-rows {
      position: absolute;
      pointer-events: none;
      top: 12px;
      bottom: 12px;
      left: 0;
      font-size: 100%;
      width: 40px;
      text-align: center;
      letter-spacing: -1px;
      border-right: 1px solid rgba(0, 0, 0, 0.66);
      user-select: none;
      counter-reset: linenumber;
      span {
        pointer-events: none;
        display: block;
        counter-increment: linenumber;
        &:before {
          content: counter(linenumber);
          color: #999;
          display: block;
          text-align: center;
        }
      }
    }
    b.name {
      position: absolute;
      top: 7px;
      right: 45px;
      z-index: 1;
      color: #999;
      pointer-events: none;
    }
    .copy-btn {
      position: absolute;
      top: 6px;
      right: 6px;
      z-index: 1;
      color: #ccc;
      background-color: #525252;
      border-radius: 6px;
      display: none;
      font-size: 14px;
      width: 32px;
      height: 24px;
      outline: none;
    }
  }
</style>
