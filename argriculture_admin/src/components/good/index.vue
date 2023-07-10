<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入内容"
            class="input-with-select"
            v-model="form.query"
            clearable
            @clear="getGoodList"
            @blur="getGoodList"
          >
            <el-select
              class="select"
              v-model="form.categoryId"
              slot="prepend"
              placeholder="请选择"
              clearable
              @clear="getGoodList"
              @change="getGoodList()"
            >
              <el-option
                v-for="item in categoryIdOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getGoodList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加商品</el-button
          >
        </el-col>
      </el-row>
      <el-table :data="goodList" border stripe style="width: 100%">
        <el-table-column prop="id" label="ID"> </el-table-column>
        <el-table-column prop="name" label="标题" width="180">
        </el-table-column>

        <el-table-column prop="categoryName" label="类别">
          <template slot-scope="scope">
            <el-tag type="success" effect="dark" size="medium">{{
              scope.row.categoryName
            }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="价格"> </el-table-column>
        <el-table-column prop="stock" label="库存"> </el-table-column>
        <el-table-column label="是否热卖">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.isHot"
              :active-value="activeHot"
              :inactive-value="inactiveHot"
              active-color="#13ce66"
              inactive-color="#ff4949"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品图片">
          <template slot-scope="scope">
            <el-image
              style="width: 60px; height: 60px"
              :src="imageUrl + scope.row.image"
              fit="fit"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="是否轮播">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.isSwiper"
              :active-value="activeSwiper"
              :inactive-value="inactiveSwiper"
              active-color="#13ce66"
              inactive-color="#ff4949"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="sales" label="销量"> </el-table-column>
        <el-table-column prop="description" label="描述">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="showDescription(scope.row.description)"
              >查看</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="sales" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="medium"
              @click="getGood(scope.row.id)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="medium"
              @click="deleteGood(scope.row.id)"
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
      title="添加商品"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClose"
      @open="addDialogOpen"
    >
      <el-form
        ref="addFormRef"
        :model="formData"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="标题" prop="name">
          <el-input
            v-model="formData.name"
            placeholder="请输入标题标题"
            :maxlength="11"
            show-word-limit
            clearable
            prefix-icon="el-icon-goods"
            suffix-icon="el-icon-goods"
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>
        <el-form-item label="类别" prop="categoryId">
          <el-select
            v-model="formData.categoryId"
            placeholder="请选择类别"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="(item, index) in categoryIdOptions"
              :key="index"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input
            v-model="formData.price"
            placeholder="请输入价格"
            clearable
            :style="{ width: '100%' }"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input
            v-model="formData.stock"
            placeholder="请输入库存"
            clearable
            :style="{ width: '100%' }"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="热卖" prop="isHot" required>
          <el-switch
            v-model="formData.isHot"
            :active-value="activeHot"
            :inactive-value="inactiveHot"
          ></el-switch>
        </el-form-item>
        <el-form-item label="商品图片" prop="image" required>
          <el-upload
            ref="image"
            :file-list="imagefileList"
            :action="imageAction"
            :headers="headers"
            :limit="1"
            accept="image/*"
            list-type="picture"
            :on-success="uploadGoodImage"
          >
            <el-button size="small" type="primary" icon="el-icon-upload"
              >点击上传</el-button
            >
          </el-upload>
        </el-form-item>
        <el-form-item label="轮播" prop="isSwiper" required>
          <el-switch
            v-model="formData.isSwiper"
            :active-value="activeSwiper"
            :inactive-value="inactiveSwiper"
          ></el-switch>
        </el-form-item>
        <el-form-item label="轮播图片" prop="isSwiper">
          <el-upload
            ref="isSwiper"
            :file-list="isSwiperfileList"
            :action="isSwiperAction"
            accept="image/*"
          >
            <el-button size="small" type="primary" icon="el-icon-upload"
              >点击上传</el-button
            >
          </el-upload>
        </el-form-item>
        <el-form-item label="编辑器" prop="description">
          <quill-editor
            class="editor"
            ref="myTextEditor"
            v-model="formData.description"
          >
          </quill-editor>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addGood">确定</el-button>
      </div>
    </el-dialog>
    <!-- 修改分类 -->
    <el-dialog
      title="修改商品"
      :visible.sync="updateDialogVisible"
      width="50%"
      @close="updateDialogClose"
      @open="updateDialogOpen"
    >
      <el-form
        ref="updateFormRef"
        :model="updatForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="标题" prop="name">
          <el-input
            v-model="updatForm.name"
            placeholder="请输入标题标题"
            show-word-limit
            clearable
            prefix-icon="el-icon-goods"
            suffix-icon="el-icon-goods"
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>
        <el-form-item label="类别" prop="categoryId">
          <el-select
            v-model="updatForm.categoryId"
            placeholder="请选择类别"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="(item, index) in categoryIdOptions"
              :key="index"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input
            v-model="updatForm.price"
            placeholder="请输入价格"
            clearable
            :style="{ width: '100%' }"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input
            v-model="updatForm.stock"
            placeholder="请输入库存"
            clearable
            :style="{ width: '100%' }"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="热卖" prop="isHot" required>
          <el-switch
            v-model="updatForm.isHot"
            :active-value="activeHot"
            :inactive-value="inactiveHot"
          ></el-switch>
        </el-form-item>
        <el-form-item label="商品图片" prop="image" required>
          <el-upload
            class="avatar-uploader"
            ref="image"
            :action="imageAction"
            :headers="headers"
            :on-success="uploadGoodUpdateImage"
            :show-file-list="false"
          >
            <img
              v-if="updatForm.image"
              :src="imageUrl + updatForm.image"
              class="avatar"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="轮播" prop="isSwiper" required>
          <el-switch
            v-model="updatForm.isSwiper"
            :active-value="activeSwiper"
            :inactive-value="inactiveSwiper"
          ></el-switch>
        </el-form-item>
        <el-form-item label="轮播图片">
          <el-upload
            class="avatar-uploader"
            :action="imageAction"
            :headers="headers"
            :on-success="uploadGoodUpdateSwiper"
            :show-file-list="false"
          >
            <img
              v-if="updatForm.swiperPic"
              :src="imageUrl + updatForm.swiperPic"
              class="avatar"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="编辑器" prop="description">
          <quill-editor
            class="editor"
            ref="myTextEditor"
            v-model="updatForm.description"
          >
          </quill-editor>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="updateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateGood">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="描述" :visible.sync="ShowDialogVisible">
      <el-input type="textarea" v-model="description" :rows="10"></el-input>
    </el-dialog>
  </div>
</template>

<script>
import {
  getGoods,
  getGoodById,
  deleteGood,
  addGood,
  updateGood,
} from "@/api/good";
import { getAllCategory } from "@/api/category";
import { getImageUrl } from "@/api/url";
export default {
  name: "good",
  data() {
    return {
      goodList: [],
      form: {
        pageNum: 1,
        pageSize: 10,
        query: "",
        categoryId: null,
      },
      activeHot: 1,
      inactiveHot: 0,
      activeSwiper: 1,
      inactiveSwiper: 0,
      imageUrl: getImageUrl(),
      good: {},
      pageSize: 1,
      total: 0,
      addDialogVisible: false,
      ShowDialogVisible: false,
      updateDialogVisible: false,
      addFormRules: [],
      addForm: {},
      formData: {
        name: undefined,
        categoryId: undefined,
        price: undefined,
        stock: undefined,
        isHot: true,
        image: null,
        isSwiper: false,
        isSwiper: null,
        description: null,
      },
      categoryId: undefined,
      rules: {
        name: [
          {
            required: true,
            message: "请输入标题标题",
            trigger: "blur",
          },
          {
            required: true,
            message: "手机号格式错误",
            trigger: "blur",
          },
        ],
        categoryId: [
          {
            required: true,
            message: "请输入类别",
            trigger: "change",
          },
        ],
        price: [
          {
            required: true,
            message: "请输入价格",
            trigger: "blur",
          },
        ],
        stock: [
          {
            required: true,
            message: "请输入库存",
            trigger: "blur",
          },
        ],
        description: [
          {
            required: true,
            message: "请输入描述",
            trigger: "blur",
          },
        ],
      },
      imageAction: "http://localhost:8081/upload/image",
      imagefileList: [],
      isSwiperAction: "http://localhost:8081/upload/image",
      isSwiperfileList: [],
      headers: {
        token: window.sessionStorage.getItem("token"),
      },
      categoryIdOptions: [],
      description: null,
      updatForm: {},
    };
  },
  created() {
    this.getGoodList();
    this.getCategoryList();
  },
  methods: {
    //获取商品列表
    getGoodList() {
      getGoods(this.form).then((res) => {
        this.goodList = res.data.records;
        this.total = res.data.total;
      });
    },
    //根据商品id获取商品
    getGood(id) {
      getGoodById(id).then((res) => {
        console.log(res);
        this.updatForm = res.data;
        this.getCategoryList();
        this.updateDialogVisible = true;
      });
    },
    //添加商品
    addGood() {
      console.log(this.formData);
      addGood(this.formData).then((res) => {
        console.log(res);
        if (!res.data) {
          this.$message.error("添加商品失败!");
          this.addDialogVisible = false;
          return;
        }
        this.getGoodList();
        this.addDialogVisible = false;
      });
    },
    //获取商品分类列表
    getCategoryList() {
      getAllCategory().then((res) => {
        this.categoryIdOptions = res.data;
      });
    },
    //修改商品个数触发函数
    handleSizeChange(val) {
      this.form.pageSize = val;
      this.getGoodList();
    },
    //修改商品页数
    handleCurrentChange(val) {
      this.form.pageNum = val;
      this.getGoodList();
    },
    //上传商品图片
    uploadGoodImage(response) {
      this.formData.image = response.data;
    },
    uploadSwiperImage(response) {
      this.formData.swiper = response.data;
    },
    addDialogClose() {
      this.$refs.addFormRef.resetFields();
      this.$refs.image.clearFiles();
      this.$refs.isSwiper.clearFiles();
    },
    addDialogOpen() {
      this.getCategoryList();
    },
    showDescription(description) {
      this.ShowDialogVisible = true;
      this.description = description;
    },
    async deleteGood(id) {
      const confirmResult = await this.$confirm(
        "此操作将永久删除该商品, 是否继续?",
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
      const res = await deleteGood(id);
      if (res.code !== 200) {
        return this.$message.error("删除商品失败！");
      }
      this.$message.success("删除成功！");
      this.getGoodList();
    },
    updateDialogClose() {
      this.$refs.updateFormRef.resetFields();
    },
    updateDialogOpen() {},
    uploadGoodUpdateImage(res) {
      this.updatForm.image = res.data;
    },
    uploadGoodUpdateSwiper(res) {
      this.updatForm.swiperPic = res.data;
    },
    updateGood() {
      updateGood(this.updatForm).then((res) => {
        if (!res.data) {
          this.$message.error("添加失败");
          this.updateDialogVisible = false;
          return;
        }
        this.$message.success("添加成功");
        this.getGoodList();
        this.updateDialogVisible = false;
      });
    },
    editorOption() {},
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
  border-color: #409eff;
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
.select {
  width: 130px;
}
</style>