<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>管理员列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入内容"
            class="input-with-select"
            v-model="form.query"
            clearable
            @clear="getAdminList"
            @blur="getAdminList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getAdminList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加管理用户</el-button
          >
        </el-col>
      </el-row>
      <el-table :data="adminList" stripe border style="width: 100%;">
        <el-table-column label="ID" prop="id" width="120"></el-table-column>
        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="密码" prop="password"></el-table-column>
        <el-table-column label="手机" prop="phone"></el-table-column>
        <el-table-column label="头像" >
            <template slot-scope="{row}">
                <el-image
                style="width: 40px;height: 40px;"
                :src="imageUrl+row.avatar"
                fit="fit"
                >

                </el-image>
            </template>
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="{row}">
                <el-button
              type="primary"
              size="medium"
              @click="updateComment(row.id)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="medium"
              @click="deleteAdmin(row.id)"
              >删除</el-button
            >
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
    <el-dialog title="添加登录用户" :visible.sync="addDialogVisible" @close="addDialogClose"  width="50%">
      <el-form :model="addForm" ref="addFormRef" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
            ref="image"
            :action="imageAction"
            :headers="headers"
            :limit="1"
            accept="image/*"
            list-type="picture"
            :on-success="uploadavatar"
          >
            <el-button size="small" type="primary" icon="el-icon-upload"
              >点击上传</el-button
            >
          </el-upload>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="addForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addAdmin">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改登录用户" :visible.sync="updataeVisible"   width="50%">
      <el-form :model="updateForm" ref="addFormRef" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="updateForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="updateForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload class="avatar-uploader"  :action="imageAction" 
          :headers="headers"
          :on-success="uploadavatar"
          :show-file-list="false">
            <img
              v-if="updateForm.avatar"
              :src="imageUrl + updateForm.avatar"
              class="avatar"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon" ></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="updateForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="updataeVisible = false">取消</el-button>
        <el-button type="primary" @click="updateAdmin">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getAdmins,getAdminById,addAdmin,deleteAdmin,updateAdmin} from "@/api/admin";
import { getImageUrl } from "@/api/url";
export default {
  name: "admin",
  data() {
    return {
      adminList: [],
      form: {
        pageNum: 1,
        pageSize: 10,
        query: "",
      },
      total:0,
      imageAction:"http://localhost:8081/upload/image",
      headers: {
        token: window.sessionStorage.getItem("token"),
      },
      imageUrl:getImageUrl(),
      addDialogVisible:false,
      addForm:{
        username:null,
        password:null,
        avatar:null,
        phone:null,
      },
      updataeVisible:false,
      updateForm:{

      },
    };
  },
  created() {
    this.getAdminList();
  },
  methods: {
    getAdminList() {
        getAdmins(this.form).then(res=>{
            console.log(res);
            this.adminList = res.data.records;
            this.total = res.data.total;
        })
    },
    addAdmin(){
      addAdmin(this.addForm).then(res=>{
        console.log(res);
        this.getAdminList();
        this.$message.success("添加成功!");
        this.addDialogVisible=false;
        
      });
    },
    uploadavatar(res){
      if(this.addDialogVisible){
        this.addForm.avatar = res.data;
      }
      if(this.updataeVisible){
        this.updateForm.avatar = res.data;
      }
    },
    handleSizeChange(val) {
      this.form.pageSize = val;
      this.getAdminList();
    },
    handleCurrentChange(val) {
      this.form.pageNum = val;
      this.getAdminList();
    },
    addDialogClose(){
      this.$refs.addFormRef.resetFields();
      this.$refs.image.clearFiles();
      this.addForm.avatar = null;
    },
    async deleteAdmin(id){
      const confirmResult = await this.$confirm(
        "此操作将永久删除该管理员, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((error) => {
        error;
      });
      if (confirmResult != "confirm") {
        return this.$message.info("已取消删除");
      }
      const res = await deleteAdmin(id);
      if (res.code !== 200) {
        return this.$message.error("删除管理员失败！");
      }
      this.$message.success("删除成功！");
      this.getAdminList();
    },
    async updateComment(id){
      const res=await getAdminById(id);
      this.updateForm=res.data;
      this.updataeVisible=true;
    },
    async updateAdmin(){
      const res=await updateAdmin(this.updateForm);
      console.log(res);
      this.$message.success("更新成功");
      this.getAdminList();
      this.updataeVisible=false;
  }
  },
  
};
</script>

<style scope>
.el-image__error{
    font-size: 6px;
}
</style>