<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <!-- 登录方式 -->
        <el-select
          clearable
          v-model="loginType"
          placeholder="请选择登录方式"
          size="small"
          style="margin-right:1rem"
        >
          <el-option
            v-for="item in typeList"
            :key="item.type"
            :label="item.desc"
            :value="item.type"
          />
        </el-select>
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入昵称"
          style="width:200px"
          @keyup.enter.native="searchUsers"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchUsers"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table border :data="userList" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column
        prop="linkAvatar"
        label="头像"
        align="center"
        width="100"
      >
        <template #default="{row}">
          <img :src="row.avatar" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="昵称"
        align="center"
        width="140"
      />
      <el-table-column
        prop="loginType"
        label="登录方式"
        align="center"
        width="80"
      >
        <template #default="{row}">
          <el-tag type="success" v-if="row.loginType == 1">邮箱</el-tag>
          <el-tag v-if="row.loginType == 2">QQ</el-tag>
          <el-tag type="danger" v-if="row.loginType == 3">微博</el-tag>
          <el-tag type="primary" v-if="row.loginType == 4">微信</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleList" label="用户角色" align="center">
        <template #default="{row}">
          <el-tag
            v-for="(item, index) of row.roleList"
            :key="index"
            style="margin-right:4px;margin-top:4px"
          >
            {{ item.roleName }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isDisable" label="禁用" align="center" width="100">
        <template #default="{row}">
          <el-switch
            v-model="row.isDisable"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="changeDisable(row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="ipAddress"
        label="登录ip"
        align="center"
        width="140"
      />
      <el-table-column
        prop="ipSource"
        label="登录地址"
        align="center"
        width="140"
      />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="130"
        align="center"
      >
        <template #default="{row}">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ row.createTime | dateTime }}
        </template>
      </el-table-column>
      <el-table-column
        prop="lastLoginTime"
        label="上次登录时间"
        width="130"
        align="center"
      >
        <template #default="{row}">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ row.lastLoginTime | dateTime }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="100">
        <template #default="{row}">
          <el-button
            type="primary"
            size="mini"
            @click="openEditModel(row)"
          >
            编辑
          </el-button>
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
    <!-- 修改对话框 -->
    <el-dialog :visible.sync="isEdit" width="30%" :close-on-click-modal="false">
      <div class="dialog-title-container" slot="title">
        修改用户
      </div>
      <el-form label-width="60px" size="medium" :model="userForm">
        <el-form-item label="昵称">
          <el-input v-model="userForm.nickname" style="width:220px" />
        </el-form-item>
        <el-form-item label="角色">
          <el-checkbox-group v-model="roleIdList">
            <el-checkbox
              v-for="item of userRoleList"
              :key="item.id"
              :label="item.id"
            >
              {{ item.roleName }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="isEdit = false">取 消</el-button>
        <el-button type="primary" @click="editUserRole">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  import {
    editUserRole,
    getRoleName,
    getUserList,
    userDisable
  } from "../../api/user";

export default {
  created() {
    this.listUsers();
    this.listRoles();
  },
  data: function() {
    return {
      loading: true,
      isEdit: false,
      userForm: {
        userInfoId: null,
        nickname: ""
      },
      loginType: null,
      userRoleList: [],
      roleIdList: [],
      userList: [],
      typeList: [
        {
          type: 1,
          desc: "邮箱"
        },
        {
          type: 2,
          desc: "QQ"
        },
        {
          type: 3,
          desc: "微博"
        },
        {
          type: 4 ,
          desc: "微信"
        }
      ],
      keywords: null,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    searchUsers() {
      this.current = 1;
      this.listUsers();
    },
    sizeChange(size) {
      this.size = size;
      this.listUsers();
    },
    currentChange(current) {
      this.current = current;
      this.listUsers();
    },
    changeDisable(user) {
      const params = {};
      params.id = user.userInfoId;
      params.isDisable = user.isDisable;
      userDisable(params).then(res => {
        if (res.data.code === 20000) {
          this.$notify.success(res.data.message);
          this.listUsers();
        } else {
          this.$notify.error(res.data.message);
          this.listUsers();
        }
      });
    },
    openEditModel(user) {
      this.roleIdList = [];
      this.userForm = JSON.parse(JSON.stringify(user));
      this.userForm.roleList.forEach(item => {
        this.roleIdList.push(item.id);
      });
      this.isEdit = true;
    },
    editUserRole() {
      this.userForm.roleIdList = this.roleIdList;
      editUserRole(this.userForm).then(res => {
        if (res.data.flag) {
          this.$notify.success({
            title: "成功",
            message: res.data.message
          });
          this.listUsers();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.data.message
          });
        }
        this.isEdit = false;
      });
    },
    listUsers() {
      const params = {
        current: this.current,
        size: this.size,
        keywords: this.keywords,
        loginType: this.loginType
      };
      getUserList(params).then(res => {
        this.userList = res.data.data.recordList;
        this.count = res.data.data.count;
        this.loading = false;
      });
    },
    listRoles() {
      getRoleName().then(res => {
        this.userRoleList = res.data.data;
        console.log(this.userRoleList)
      });
    }
  },
  watch: {
    loginType() {
      this.current = 1;
      this.listUsers();
    }
  }
};
</script>
