<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 文章状态 -->
    <div class="article-status-menu">
      <span style="font-weight: bold;color: red">状态:</span>
      <span @click="changeStauts('all')" :class="isActive('all')">全部</span>
      <span @click="changeStauts('public')" :class="isActive('public')">
        公开
      </span>
      <span @click="changeStauts('secret')" :class="isActive('secret')">
        私密
      </span>
      <span @click="changeStauts('draft')" :class="isActive('draft')">
        草稿箱
      </span>
      <span @click="changeStauts('delete')" :class="isActive('delete')">
        回收站
      </span>
    </div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        v-if="isDelete == 0"
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="articleIdList.length == 0"
        @click="updateIsDelete = true"
      >
        批量删除
      </el-button>
      <el-button
        v-else
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="articleIdList.length === 0"
        @click="remove = true"
      >
        批量删除
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <!-- 文章类型 -->
        <el-select
          clearable
          v-model="type"
          placeholder="请选择文章类型"
          size="small"
          style="margin-right:1rem"
        >
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <!-- 分类 -->
        <el-select
          clearable
          size="small"
          v-model="categoryId"
          filterable
          placeholder="请选择分类"
          style="margin-right:1rem"
        >
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.categoryName"
            :value="item.id"
          />
        </el-select>
        <!-- 标签 -->
        <el-select
          clearable
          size="small"
          v-model="tagId"
          filterable
          placeholder="请选择标签"
          style="margin-right:1rem"
        >
          <el-option
            v-for="item in tagList"
            :key="item.id"
            :label="item.tagName"
            :value="item.id"
          />
        </el-select>
        <!-- 文章名 -->
        <el-input
          clearable
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入文章名"
          style="width:200px"
          @keyup.enter.native="searchArticles"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchArticles"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="articleList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 文章修改时间 -->
      <el-table-column
        prop="articleCover"
        label="文章封面"
        width="180"
        align="center"
      >
        <template #default="{row}">
          <el-image
            class="article-cover"
            :src="
              row.articleCover
                ? row.articleCover
                : 'https://www.static.talkxj.com/articles/c5cc2b2561bd0e3060a500198a4ad37d.png'
            "
          />
          <i
            v-if="row.status === 1"
            class="iconfont el-icon-mygongkai article-status-icon"
          />
          <i
            v-if="row.status === 2"
            class="iconfont el-icon-mymima article-status-icon"
          />
          <i
            v-if="row.status === 3"
            class="iconfont el-icon-mycaogaoxiang article-status-icon"
          />
        </template>
      </el-table-column>
      <!-- 文章标题 -->
      <el-table-column prop="articleTitle" label="标题" align="center" />
      <!-- 文章分类 -->
      <el-table-column
        prop="categoryName"
        label="分类"
        width="110"
        align="center"
      />
      <!-- 文章标签 -->
      <el-table-column
        prop="tagDTOList"
        label="标签"
        width="170"
        align="center"
      >
        <template #default="{row}">
          <el-tag
            v-for="item of row.tagDTOList"
            :key="item.tagId"
            style="margin-right:0.2rem;margin-top:0.2rem"
          >
            {{ item.tagName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 文章浏览量 -->
      <el-table-column
        prop="viewsCount"
        label="浏览量"
        width="70"
        align="center"
      >
        <template #default="{row}">
          <span v-if="row.viewsCount">
            {{ row.viewsCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章点赞量 -->
      <el-table-column
        prop="likeCount"
        label="点赞量"
        width="70"
        align="center"
      >
        <template #default="{row}">
          <span v-if="row.likeCount">
            {{ row.likeCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章类型 -->
      <el-table-column prop="type" label="类型" width="80" align="center">
        <template #default="{row}">
          <el-tag :type="articleType(row.type).tagType">
            {{ articleType(row.type).name }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 文章发表时间 -->
      <el-table-column
        prop="createTime"
        label="发表时间"
        width="130"
        align="center"
      >
        <template #default="{row}">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 文章置顶 -->
      <el-table-column prop="isTop" label="置顶" width="80" align="center">
        <template #default="{row}">
          <el-switch
            v-model="row.isTop"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :disabled="row.isDelete === 1"
            :active-value="1"
            :inactive-value="0"
            @change="changeTop(row)"
          />
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="150">
        <template #default="{row}">
          <el-button
            type="primary"
            size="mini"
            @click="editArticle(row.id)"
            v-if="row.isDelete === 0"
          >
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @confirm="updateArticleDelete(row.id)"
            v-if="row.isDelete === 0"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
          <el-popconfirm
            title="确定恢复吗？"
            v-if="row.isDelete === 1"
            @confirm="updateArticleDelete(row.id)"
          >
            <el-button size="mini" type="success" slot="reference">
              恢复
            </el-button>
          </el-popconfirm>
          <el-popconfirm
            style="margin-left:10px"
            v-if="row.isDelete == 1"
            title="确定彻底删除吗？"
            @confirm="deleteArticles(row.id)"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[10,30,50,100]"
      layout="total, sizes, prev, pager, next, jumper"
    />
    <!-- 批量逻辑删除对话框 -->
    <el-dialog :visible.sync="updateIsDelete" width="30%" :close-on-click-modal="false">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="updateIsDelete = false">取 消</el-button>
        <el-button type="primary" @click="updateArticleDelete(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 批量彻底删除对话框 -->
    <el-dialog :visible.sync="remove" width="30%" :close-on-click-modal="false">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否彻底删除选中项？</div>
      <div slot="footer">
        <el-button @click="remove = false">取 消</el-button>
        <el-button type="primary" @click="deleteArticles(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  import {articleTop, getArticleList, logicDeleteArticles} from "../../api/article";

export default {
  created() {
    this.listArticles();
    this.listCategories();
    this.listTags();
  },
  data: function() {
    return {
      loading: true,
      updateIsDelete: false,
      remove: false,
      typeList: [
        {
          value: 1,
          label: "原创"
        },
        {
          value: 2,
          label: "转载"
        },
        {
          value: 3,
          label: "翻译"
        }
      ],
      activeStatus: "all",
      articleList: [],
      articleIdList: [],
      categoryList: [],
      tagList: [],
      keywords: null,
      type: null,
      categoryId: null,
      tagId: null,
      isDelete: 0,
      status: null,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    selectionChange(articleList) {
      this.articleIdList = [];
      articleList.forEach(item => {
        this.articleIdList.push(item.id);
      });
    },
    searchArticles() {
      this.current = 1;
      this.listArticles();
    },
    editArticle(id) {
      this.$router.push({ path: "/articles/" + id });
    },
    updateArticleDelete(id) {
      let param = {};
      if (id != null) {
        param.idList = [id];
      } else {
        param.idList = this.articleIdList;
      }
      param.isDelete = this.isDelete === 0 ? 1 : 0;
      logicDeleteArticles(param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listArticles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.updateIsDelete = false;
      });
    },
    // 批量彻底删除
    deleteArticles(id) {
      var param = {};
      if (id == null) {
        param = { data: this.articleIdList };
      } else {
        param = { data: [id] };
      }
      this.axios.delete("/api/admin/articles", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listArticles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.remove = false;
      });
    },
    sizeChange(size) {
      this.size = size;
      this.listArticles();
    },
    currentChange(current) {
      this.current = current;
      this.listArticles();
    },
    changeStauts(status) {
      switch (status) {
        case "all":
          this.isDelete = 0;
          this.status = null;
          break;
        case "public":
          this.isDelete = 0;
          this.status = 1;
          break;
        case "secret":
          this.isDelete = 0;
          this.status = 2;
          break;
        case "draft":
          this.isDelete = 0;
          this.status = 3;
          break;
        case "delete":
          this.isDelete = 1;
          this.status = null;
          break;
      }
      this.activeStatus = status;
    },
    // 置顶
    changeTop(article) {
      articleTop({ id: article.id, isTop: article.isTop }).then(({ data }) => {
        console.log('data',data)
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: "置顶成功"
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
          article.isTop = article.isTop === 0 ? 1 : 0;
        }
        this.remove = false;
      });
    },
    listArticles() {
      const params = {
        current: this.current,
        size: this.size,
        keywords: this.keywords,
        categoryId: this.categoryId,
        status: this.status,
        tagId: this.tagId,
        type: this.type,
        isDelete: this.isDelete
      };
      getArticleList(params).then(({ data }) => {
        this.articleList = data.data.recordList;
        console.log(this.articleList)
        this.count = data.data.count;
        this.loading = false;
      });
    },
    listCategories() {
      this.axios.get("/api/admin/categories/search").then(({ data }) => {
        this.categoryList = data.data;
      });
    },
    listTags() {
      this.axios.get("/api/admin/tags/search").then(({ data }) => {
        this.tagList = data.data;
      });
    }
  },
  watch: {
    type() {
      this.current = 1;
      this.listArticles();
    },
    categoryId() {
      this.current = 1;
      this.listArticles();
    },
    tagId() {
      this.current = 1;
      this.listArticles();
    },
    status() {
      this.current = 1;
      this.listArticles();
    },
    isDelete() {
      this.current = 1;
      this.listArticles();
    }
  },
  computed: {
    articleType() {
      return function(type) {
        var tagType = "";
        var name = "";
        switch (type) {
          case 1:
            tagType = "danger";
            name = "原创";
            break;
          case 2:
            tagType = "success";
            name = "转载";
            break;
          case 3:
            tagType = "primary";
            name = "翻译";
            break;
        }
        return {
          tagType: tagType,
          name: name
        };
      };
    },
    isActive() {
      return function(status) {
        return this.activeStatus == status ? "active-status" : "status";
      };
    }
  }
};
</script>

<style scoped>
.operation-container {
  margin-top: 1.5rem;
}
.article-status-menu {
  font-size: 14px;
  margin-top: 40px;
  color: #999;
}
.article-status-menu span {
  margin-right: 24px;
}
.status {
  cursor: pointer;
}
.active-status {
  cursor: pointer;
  color: #333;
  font-weight: bold;
}
.article-cover {
  position: relative;
  width: 100%;
  height: 90px;
  border-radius: 4px;
}
.article-cover::after {
  content: "";
  background: rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.article-status-icon {
  color: #fff;
  font-size: 1.5rem;
  position: absolute;
  right: 1rem;
  bottom: 1.4rem;
}
</style>
