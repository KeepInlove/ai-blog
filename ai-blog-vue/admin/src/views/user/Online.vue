<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入用户昵称"
          style="width:200px"
          @keyup.enter.native="listOnlineUsers"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="listOnlineUsers"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 权限列表 -->
    <el-table v-loading="loading" :data="userList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="avatar" label="头像" align="center" width="100">
        <template #default="{row}">
          <img :src="row.avatar" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" align="center" />
      <el-table-column prop="ipAddress" label="ip地址" align="center" />
      <el-table-column
        prop="ipSource"
        label="登录地址"
        align="center"
        width="200"
      />
      <el-table-column
        prop="browser"
        label="浏览器"
        align="center"
        width="160"
      />
      <el-table-column prop="os" label="操作系统" align="center" />
      <el-table-column
        prop="lastLoginTime"
        label="登录时间"
        align="center"
        width="200"
      >
        <template #default="{row}">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ row.lastLoginTime | dateTime }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150">
        <template #default="{row}">
          <el-popconfirm
            title="确定下线吗？"
            style="margin-left:10px"
            @confirm="removeOnlineUser(row)"
          >
            <el-button size="mini" type="text" slot="reference">
              <i class="el-icon-delete" /> 下线
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
      :page-sizes="[10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
    />
  </el-card>
</template>

<script>
  import {getOnlineUserList, removeOnlineUser} from "../../api/user";

export default {
  created() {
    this.listOnlineUsers();
  },
  data() {
    return {
      loading: true,
      userList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      isCheck: false,
      optLog: {}
    };
  },
  methods: {
    listOnlineUsers() {
      const params = {
        current: this.current,
        size: this.size,
        keywords: this.keywords
      };
      getOnlineUserList(params).then(res => {
        this.userList = res.data.data.recordList;
        this.count = res.data.data.count;
        this.loading = false;
      });
    },
    sizeChange(size) {
      this.size = size;
      this.listOnlineUsers();
    },
    currentChange(current) {
      this.current = current;
      this.listOnlineUsers();
    },
    // 强制踢出在线用户
    removeOnlineUser(user) {
      removeOnlineUser(user.userInfoId).then(res => {
        if (res.data.flag) {
          this.$notify.success({
            title: "成功",
            message: res.data.message
          });
          this.listOnlineUsers();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.data.message
          });
        }
      });
    }
  },
  computed: {
    tagType() {
      return function(type) {
        switch (type) {
          case "GET":
            return "";
          case "POST":
            return "success";
          case "PUT":
            return "warning";
          case "DELETE":
            return "danger";
        }
      };
    }
  }
};
</script>

<style scoped>
label {
  font-weight: bold !important;
}
</style>
