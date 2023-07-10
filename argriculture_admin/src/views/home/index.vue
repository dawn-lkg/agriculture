<template>
  <div id="home">
    <el-container>
      <el-header>
        <div class="header">
          <span>商城管理系统</span>
          <el-dropdown @command="backLogin">
            <span class="el-dropdown-link">
              
              <div class="item">
                <el-avatar
                  :src="info.avatar"
                  size="small"
                ></el-avatar>
                <div>{{ info.username }}</div>
              </div>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-back">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside :width="isCollapse ? '64px' : '200px'">
          <div class="toggle-button" @click="isCollapse = !isCollapse">|||</div>
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            :collapse="isCollapse"
            :collapse-transition="false"
            :router="true"
            background-color="#333744"
            text-color="#fff"
            active-text-color="#409eff"
          >
            <el-menu-item
              v-for="item in router"
              :key="item.id"
              :index="item.path"
            >
              <i class="el-icon-menu"></i>
              <span slot="title">{{ item.name }}</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { getInfo } from "@/api/admin";
import { getImageUrl } from "@/api/url";
export default {
  name: "home",
  data() {
    return {
      isCollapse: false,
      router: [
        {
          id: "101",
          path: "/good",
          name: "商品管理",
          icon: "el-icon -s-management",
        },
        {
          id: "102",
          path: "/category",
          name: "分类管理",
          icon: "el-icon -s-management",
        },
        {
          id: "103",
          path: "/user",
          name: "用户管理",
          icon: "el-icon -s-management",
        },
        {
          id: "104",
          path: "/admin",
          name: "管理员管理",
          icon: "el-icon -s-management",
        },
        {
          id: "105",
          path: "/video",
          name: "短视频管理",
          icon: "el-icon -s-management",
        },
        {
          id: "106",
          path: "/order",
          name: "订单管理",
          icon: "el-icon -s-management",
        },
        {
          id: "107",
          path: "/comment",
          name: "评论管理",
          icon: "el-icon -s-management",
        },
        {
          id: "108",
          path: "/echart",
          name:"数据分析",
          icon: "el-icon -s-management"
        }
      ],
      info: {},
      imageBaseUrl:null,
    };
  },
  created() {
    this.getInfo();
    this.imageBaseUrl=getImageUrl();
  },
  methods: {
    getInfo() {
      getInfo().then((res) => {
        this.info = res.data;
        this.info.avatar=getImageUrl()+this.info.avatar;
      });
    },
    backLogin(c){
      window.sessionStorage.clear();
      this.$router.push("/login");
    }
  },
};
</script>

<style scoped>
#home {
  height: 100%;
}
.el-container {
  height: 100%;
}
.el-header {
  width: 100%;
  background-color: #373b41;
  padding-right: 0;
}
.el-aside .el-menu {
  border-right: 0;
}
.el-aside {
  height: 100%;
  background-color: #333744;
}
.iconfont {
  margin-right: 10px;
}
.header {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #fff;
}
.el-header .el-avatar {
  margin-right: 8px;
}
.el-header .item {
  display: flex;
  height: 100%;
  align-items: center;
  font-size: 14px;
  padding: 0 20px;
  cursor: pointer;
}
.el-header .item:hover {
  background-color: rgb(41, 44, 54);
}
.toggle-button {
  background: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
  user-select: none;
}
.el-dropdown {
  height: 100%;
  color: #fff;
}
.el-card {
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
}
</style>