<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>分类列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入内容"
            class="input-with-select"
            v-model="form.query"
            clearable
            @clear="getCategoryList"
            @blur="getCategoryList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getCategoryList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加分类</el-button
          >
        </el-col>
      </el-row>
      <el-table :data="categoryList" border stripe style="width: 100%">
        <el-table-column label="ID" prop="id" width="180"></el-table-column>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="图标" prop="pic">
          <template slot-scope="scope">
            <el-image
              style="width: 60px; height: 60px"
              :src="imageUrl + scope.row.pic"
              fit="fit"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="medium"
              @click="getCategory(scope.row.id)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="medium"
              @click="deleteCategory(scope.row.id)"
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
    <el-dialog
      title="添加分类"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClose"
      @open="addDialogOpen"
    >
      <el-form
        ref="addFormRef"
        :model="addForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="分类名称" prop="name">
          <el-input
            v-model="addForm.name"
            placeholder="请输入分类名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="图标上传" prop="pic" required>
          <el-upload
            ref="image"
            :action="imageAction"
            :headers="headers"
            :limit="1"
            accept="image/*"
            list-type="picture"
            :on-success="uploadCategoryIcon"
          >
            <el-button size="small" type="primary" icon="el-icon-upload"
              >点击上传</el-button
            >
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addCategory">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="修改分类"
      :visible.sync="updateDialogVisible"
      width="50%"
      @close="updateDialogClose"
      @open="updateDialogOpen"
    >
      <el-form
        ref="updateFormRef"
        :model="updateForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="分类名称" prop="name">
          <el-input
            v-model="updateForm.name"
            placeholder="请输入分类名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="图标上传" prop="pic" required>
          <el-upload class="avatar-uploader"  :action="imageAction" 
          :headers="headers"
          :on-success="uploadCategoryPic"
          :show-file-list="false">
            <img
              v-if="updateForm.pic"
              :src="imageUrl + updateForm.pic"
              class="avatar"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon" ></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="updateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateCategory">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getCategorys,
  getCategoryById,
  deleteCategory,
  addCategory,
  updateCategory,
} from "@/api/category";
import { getImageUrl } from "@/api/url";
export default {
  name: "category",
  data() {
    return {
      categoryList: [],
      form: {
        pageNum: 1,
        pageSize: 10,
        query: "",
      },
      total: 0,
      imageUrl: getImageUrl(),
      addDialogVisible: false,
      updateDialogVisible: false,
      rules: {
        name: [
          {
            required: true,
            message: "请输入分类名称",
            trigger: "blur",
          },
        ],
      },
      addForm: {
        name: null,
        pic: null,
      },
      updateForm: {},
      headers: {
        token: window.sessionStorage.getItem("token"),
      },
      imageAction: "http://localhost:8081/upload/image",
    };
  },
  created() {
    this.getCategoryList();
  },
  methods: {
    getCategoryList() {
      getCategorys(this.form).then((res) => {
        console.log(res);
        this.categoryList = res.data.records;
        this.total = res.data.total;
      });
    },
    getCategory(id) {
      getCategoryById(id).then((res) => {
        this.updateForm = res.data;
        this.updateDialogVisible=true;
      });
    },
    async deleteCategory(id) {
      const confirmResult = await this.$confirm(
        "此操作将永久删除该分类, 是否继续?",
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
      const res = await deleteCategory(id);
      if (res.code !== 200) {
        return this.$message.error("删除分类失败！");
      }
      this.$message.success("删除成功！");
      this.getCategoryList();
    },
    updateCategory(){
      updateCategory(this.updateForm).then(res=>{
        if(!res.data){
          this.$message.error("修改失败");
          this.updateDialogVisible=false;
        }
        this.$message.success("修改成功!");
        this.updateDialogVisible=false;
        this.getCategoryList();
      })
    },
    addCategory() {
      addCategory(this.addForm).then((res) => {
        console.log(res);
        if (!res.data) {
          this.$message.error("添加失败");
          this.addDialogVisible = false;
          return;
        }
        this.$message.success("添加成功!");
        this.addDialogVisible = false;
        this.getCategoryList();
      });
    },
    handleSizeChange(val) {
      this.form.pageSize = val;
      this.getCategoryList();
    },
    handleCurrentChange(val) {
      this.form.pageNum = val;
      this.getCategoryList();
    },
    addDialogClose() {
      this.$refs.addFormRef.resetFields();
      this.$refs.image.clearFiles();
      this.addForm.pic = null;
    },
    uploadCategoryPic(){

    },
    updateDialogClose(){

    },
    uploadCategoryIcon(res) {
      this.addForm.pic = res.data;
    },
    updateDialogOpen(){

    },
    addDialogOpen() {},
  },
};
</script>

<style scoped>
.avatar-uploader .el-upload {
    border: 1px dashed #000;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    border: 1px dashed #d9d9d9;
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>