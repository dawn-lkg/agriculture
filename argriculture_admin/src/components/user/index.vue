<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入内容"
            class="input-with-select"
            v-model="form.query"
            clearable
            @clear="getUserList"
            @blur="getUserList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getUserList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加用户</el-button
          >
        </el-col>
      </el-row>
      <el-table :data="userList" border stripe style="width: 100%">
        <el-table-column label="ID" prop="id"></el-table-column>
        <el-table-column
          label="用户名"
          prop="name"
          width="180"
        ></el-table-column>
        <el-table-column
          label="OpenId"
          prop="openid"
          width="180"
        ></el-table-column>
        <el-table-column label="头像" prop="avatar" width="180">
          <template slot-scope="scope">
            <el-image
              style="width: 60px; height: 60px"
              :src="scope.row.avatar"
              fit="fit"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="余额" prop="balance" width="180">
            <template slot-scope="{row,$index}">
                <div v-if="row.show" @dblclick="updateShow($index)">{{row.balance}}</div>
                <div v-else><el-input  v-model.number="row.balance" @blur="changeBalance($index)"></el-input></div>
            </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-sizes="[2, 5, 10, 20, 50, 100, 200]"
        :current-page="form.pageNum"
        :page-size="form.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
import { getUsers, getUserById, deleteUser, addUser,updateUser } from "@/api/user";
import { getImageUrl } from "@/api/url";

export default {
  name: "user",
  data() {
    return {
      userList: [],
      form: {
        pageNum: 1,
        pageSize: 10,
        query: "",
      },
      total: 0,
      showList:{},
      imageUrl: getImageUrl(),
      headers: {
        token: window.sessionStorage.getItem("token"),
      },
      imageAction: "http://localhost:8081/upload/image",
      addDialogVisible: false,
    };
  },
  created() {
    this.getUserList();
    
  },
  methods: {
    getUserList() {
      getUsers(this.form).then((res) => {
        console.log(res);
        this.userList = res.data.records;
        this.total = res.data.total;
        this.userList.map((user) =>{
            this.$set(user,'show',true);
        })
      });
    },
    handleSizeChange(val) {
      this.form.pageSize = val;
      this.getUserList();
    },
    handleCurrentChange(val) {
      this.form.pageNum = val;
      this.getUserList();
    },
    updateShow(index){
        this.userList[index].show=!this.userList[index].show;
    },
    changeBalance(index){
        this.updateShow(index);
        updateUser(this.userList[index]).then(res=>{
            if(res.data){
                this.$message.success("更新成功！");
            }
        })
    }
  },
};
</script>

<style scoped>
</style>