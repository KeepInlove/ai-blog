<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openModel(null)"
      >
        新增菜单
      </el-button>
      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入菜单名"
          style="width:200px"
          @keyup.enter.native="listMenus"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="listMenus"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 权限列表 -->
    <el-table
      v-loading="loading"
      :data="menuList"
      row-key="id"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <!-- 菜单名称 -->
      <el-table-column prop="name" label="菜单名称" width="140" />
      <!-- 菜单icon -->
      <el-table-column prop="icon" align="center" label="图标" width="100">
        <template #default="{row}">
          <i :class="'iconfont ' + row.icon" />
        </template>
      </el-table-column>
      <!-- 菜单排序 -->
      <el-table-column
        prop="orderNum"
        align="center"
        label="排序"
        width="100"
      />
      <!-- 访问路径 -->
      <el-table-column prop="path" label="访问路径" />
      <!-- 组件路径 -->
      <el-table-column prop="component" label="组件路径" />
      <!-- 是否隐藏 -->
      <el-table-column prop="isHidden" label="隐藏" align="center" width="80">
        <template #default="{row}">
          <el-switch
            v-model="row.isHidden"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="changeDisable(row)"
          />
        </template>
      </el-table-column>
      <!-- 创建时间 -->
      <el-table-column
        prop="createTime"
        label="创建时间"
        align="center"
        width="150"
      >
        <template #default="{row}">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" width="200">
        <template #default="{row}">
          <el-button
            type="text"
            size="mini"
            @click="openModel(row, 1)"
            v-if="row.children"
          >
            <i class="el-icon-plus" /> 新增
          </el-button>
          <el-button type="text" size="mini" @click="openModel(row, 2)">
            <i class="el-icon-edit" /> 修改
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @confirm="deleteMenu(row.id)"
          >
            <el-button size="mini" type="text" slot="reference">
              <i class="el-icon-delete" /> 删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增模态框 -->
    <el-dialog :visible.sync="addMenu" width="30%" top="12vh" :close-on-click-modal="false">
      <div class="dialog-title-container" slot="title" ref="menuTitle" />
      <el-form label-width="80px" size="medium" :model="menuForm">
        <!-- 菜单类型 -->
        <el-form-item label="菜单类型" v-if="show">
          <el-radio-group v-model="isCatalog">
            <el-radio :label="true">目录</el-radio>
            <el-radio :label="false">一级菜单</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 菜单名称 -->
        <el-form-item label="菜单名称">
          <el-input v-model="menuForm.name" style="width:220px" />
        </el-form-item>
        <!-- 菜单图标 -->
        <el-form-item label="菜单图标">
          <el-popover placement="bottom-start" width="300" trigger="click">
            <el-row>
              <el-col
                v-for="(item, index) of iconList"
                :key="index"
                :md="12"
                :gutter="10"
              >
                <div class="icon-item" @click="checkIcon(item)">
                  <i :class="'iconfont ' + item" /> {{ item }}
                </div>
              </el-col>
            </el-row>
            <el-input
              :prefix-icon="'iconfont ' + menuForm.icon"
              slot="reference"
              v-model="menuForm.icon"
              style="width:220px"
            />
          </el-popover>
        </el-form-item>
        <!-- 组件路径 -->
        <el-form-item label="组件路径" v-show="!isCatalog">
          <el-input v-model="menuForm.component" style="width:220px" />
        </el-form-item>
        <!-- 路由地址 -->
        <el-form-item label="访问路径">
          <el-input v-model="menuForm.path" style="width:220px" />
        </el-form-item>
        <!-- 显示排序 -->
        <el-form-item label="显示排序">
          <el-input-number
            v-model="menuForm.orderNum"
            controls-position="right"
            :min="1"
            :max="10"
          />
        </el-form-item>
        <!-- 显示状态 -->
        <el-form-item label="显示状态">
          <el-radio-group v-model="menuForm.isHidden">
            <el-radio :label="0">显示</el-radio>
            <el-radio :label="1">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addMenu = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateMenu">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  import {addOrUpdateMenu, deleteMenu, getMenuList} from "../../api/system";

export default {
  created() {
    this.listMenus();
  },
  data() {
    return {
      keywords: "",
      loading: true,
      addMenu: false,
      isCatalog: true,
      show: true,
      menuList: [],
      menuForm: {
        id: null,
        name: "",
        icon: "",
        component: "",
        path: "",
        orderNum: 1,
        parentId: null,
        isHidden: 0
      },
      iconList: [
        "el-icon-myshouye",
        "el-icon-myfabiaowenzhang",
        "el-icon-myyonghuliebiao",
        "el-icon-myxiaoxi",
        "el-icon-myliuyan",
        "el-icon-myshouye",
        "el-icon-myfabiaowenzhang",
        "el-icon-myyonghuliebiao",
        "el-icon-myxiaoxi",
        "el-icon-myliuyan"
      ]
    };
  },
  methods: {
    changeDisable(row) {
      this.$notify.error({
        title: "失败",
        message: "禁止操作"
      })
      row.isHidden = row.isHidden === 1 ? 0 : 1;
    },
    listMenus() {
      getMenuList({ keywords: this.keywords }).then(res => {
        this.menuList = res.data.data;
        this.loading = false;
      });
    },
    openModel(menu, type) {
      if (menu) {
        this.show = false;
        this.isCatalog = false;
        switch (type) {
          case 1:
            this.menuForm = {
              id: null,
              name: "",
              icon: "",
              component: "",
              path: "",
              orderNum: 1,
              parentId: null,
              isHidden: 0
            };
            this.$refs.menuTitle.innerHTML = "新增菜单";
            this.menuForm.parentId = JSON.parse(JSON.stringify(menu.id));
            break;
          case 2:
            this.$refs.menuTitle.innerHTML = "修改菜单";
            this.menuForm = JSON.parse(JSON.stringify(menu));
            break;
        }
      } else {
        this.$refs.menuTitle.innerHTML = "新增菜单";
        this.show = true;
        this.menuForm = {
          id: null,
          name: "",
          icon: "",
          component: "Layout",
          path: "",
          orderNum: 1,
          parentId: null,
          isHidden: 0
        };
      }
      this.addMenu = true;
    },
    checkIcon(icon) {
      this.menuForm.icon = icon;
    },
    saveOrUpdateMenu() {
      if (this.menuForm.name.trim() === "") {
        this.$message.error("菜单名不能为空");
        return false;
      }
      if (this.menuForm.icon.trim() === "") {
        this.$message.error("菜单icon不能为空");
        return false;
      }
      if (this.menuForm.component.trim() === "") {
        this.$message.error("菜单组件路径不能为空");
        return false;
      }
      if (this.menuForm.path.trim() === "") {
        this.$message.error("菜单访问路径不能为空");
        return false;
      }
      addOrUpdateMenu(this.menuForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: "操作成功"
          });
          this.listMenus();
        } else {
          this.$notify.error({
            title: "失败",
            message: "操作失败"
          });
        }
        this.addMenu = false;
      });
    },
    deleteMenu(id) {
      deleteMenu(id).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: "删除成功"
          });
          this.listMenus();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.icon-item {
  cursor: pointer;
  padding: 0.5rem 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
