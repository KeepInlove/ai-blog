<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openModel(null)"
      >
        新增
      </el-button>
      <el-button
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="linkIdList.length == 0"
        @click="deleteFlag = true"
      >
        批量删除
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入友链名"
          style="width:200px"
          @keyup.enter.native="searchLinks"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchLinks"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="linkList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <el-table-column
        prop="linkAvatar"
        label="友链头像"
        align="center"
        width="180"
      >
        <template #default="{row}">
          <img :src="row.linkAvatar" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column prop="linkName" label="友链名称" align="center" />
      <el-table-column prop="linkAddress" label="友链地址" align="center" />
      <el-table-column prop="linkIntro" label="友链介绍" align="center" />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="140"
        align="center"
      >
        <template #default="{row}">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template #default="{row}">
          <el-button type="primary" size="mini" @click="openModel(row)">
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:1rem"
            @confirm="deleteLink(row.id)"
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
      :page-sizes="[5,10,30,50,100]"
      layout="total, sizes, prev, pager, next, jumper"
    />
    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="deleteFlag" width="30%" :close-on-click-modal="false">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="deleteFlag = false">取 消</el-button>
        <el-button type="primary" @click="deleteLink(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 添加对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="30%" :close-on-click-modal="false">
      <div class="dialog-title-container" slot="title" ref="linkTitle" />
      <el-form label-width="80px" size="medium" :model="linkForm">
        <el-form-item label="友链名称">
          <el-input style="width:250px" v-model="linkForm.linkName" />
        </el-form-item>
        <el-form-item label="友链头像">
          <el-input style="width:250px" v-model="linkForm.linkAvatar" />
        </el-form-item>
        <el-form-item label="友链地址">
          <el-input style="width:250px" v-model="linkForm.linkAddress" />
        </el-form-item>
        <el-form-item label="友链介绍">
          <el-input style="width:250px" v-model="linkForm.linkIntro" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditLinks">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  import {addOrEditLinks, getLinkList} from "../../api/user";

export default {
  created() {
    this.listLinks();
  },
  data: function() {
    return {
      loading: true,
      deleteFlag: false,
      addOrEdit: false,
      linkIdList: [],
      linkList: [],
      linkForm: {
        id: null,
        linkName: "",
        linkAvatar: "",
        linkIntro: "",
        linkAddress: ""
      },
      keywords: null,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    selectionChange(linkList) {
      this.linkIdList = [];
      linkList.forEach(item => {
        this.linkIdList.push(item.id);
      });
    },
    searchLinks() {
      this.current = 1;
      this.listLinks();
    },
    sizeChange(size) {
      this.size = size;
      this.listLinks();
    },
    currentChange(current) {
      this.current = current;
      this.listLinks();
    },
    deleteLink(id) {
      var param = {};
      if (id == null) {
        param = { data: this.linkIdList };
      } else {
        param = { data: [id] };
      }
      console.log(param)
      this.axios.delete("/api/admin/links", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listLinks();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.deleteFlag = false;
      });
    },
    openModel(link) {
      if (link != null) {
        this.linkForm = JSON.parse(JSON.stringify(link));
        this.$refs.linkTitle.innerHTML = "修改友链";
      } else {
        this.linkForm.id = null;
        this.linkForm.linkName = "";
        this.linkForm.linkAvatar = "";
        this.linkForm.linkIntro = "";
        this.linkForm.linkAddress = "";
        this.$refs.linkTitle.innerHTML = "添加友链";
      }
      this.addOrEdit = true;
    },
    addOrEditLinks() {
      if (this.linkForm.linkName.trim() === "") {
        this.$message.error("友链名称不能为空");
        return false;
      }
      if (this.linkForm.linkAvatar.trim() === "") {
        this.$message.error("友链头像不能为空");
        return false;
      }
      if (this.linkForm.linkIntro.trim() === "") {
        this.$message.error("友链介绍不能为空");
        return false;
      }
      if (this.linkForm.linkAddress.trim() === "") {
        this.$message.error("友链地址不能为空");
        return false;
      }
      addOrEditLinks(this.linkForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listLinks();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.addOrEdit = false;
      });
    },
    listLinks() {
      const params = {
        current: this.current,
        size: this.size,
        keywords: this.keywords
      };
      getLinkList(params).then(({ data }) => {
        this.linkList = data.data.recordList;
        this.count = data.data.count;
        this.loading = false;
      });
    }
  }
};
</script>
