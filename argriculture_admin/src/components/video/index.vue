<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>视频列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入内容"
            class="input-with-select"
            v-model="form.query"
            clearable
            @clear="getVideoList"
            @blur="getVideoList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getVideoList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加视频</el-button
          >
        </el-col>
      </el-row>
      <el-table :data="videoList" border stripe>
        <el-table-column label="ID" prop="id"></el-table-column>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="描述" prop="description"></el-table-column>
        <el-table-column label="视频封面">
          <template slot-scope="{ row }">
            <el-image :src="imageUrl + row.coverUrl"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="是否审核">
          <template slot-scope="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="1"
              :inactive-value="0"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="视频">
          <template slot-scope="{ row }">
            <el-button type="primary" @click="viewVideo(row.videoUrl)"
              >查看视频</el-button
            >
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="{ row }">
            <el-button type="primary" @click="getVideo(row.id)">修改</el-button>
            <el-button type="danger" @click="deleteVideo(row.id)"
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
    <el-dialog title="视频" :visible.sync="showVisbleVideo" width="30%">
      <video
        class="video"
        :src="videoBaseUrl + url"
        width="100%"
        controls
      ></video>
    </el-dialog>
    <el-dialog title="添加视频" :visible.sync="addDialogVisible" width="50%">
      <el-form :model="addForm" ref="addFormRef" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="addForm.title" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="description" prop="description">
          <el-input
            type="textarea"
            :rows="2"
            v-model="addForm.description"
            placeholder="请输入描述"
          ></el-input>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :action="videoAction"
            :on-success="uploadVideo"
            :headers="headers"
            name="file"
          >
            <el-button size="small" type="primary" icon="el-icon-upload"
              >点击上传</el-button
            >
            <video class="video-upload"
              v-if="addForm.videoUrl != null"
              :src="
                addForm.videoUrl == null
                  ? null
                  : videoBaseUrl + addForm.videoUrl
              "
              width="100%"
              controls
            ></video>
          </el-upload>
        </el-form-item>
        <el-form-item label="审核">
          <el-switch
            active-value="1"
            inactive-value="0"
            v-model="addForm.status"
          ></el-switch>
        </el-form-item>
        <el-form-item label="商品ID">
          <el-button type="primary" @click="getGoodList"
            >选择商品{{ addForm.goodId == null ? null : ":" + addForm.goodId }}
          </el-button>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addVideo">确定</el-button>
      </div>
      <el-dialog
        title="选择商品"
        append-to-body
        :visible.sync="selectGoodVisble"
        show-overflow-tooltip
      >
        <el-table :data="goodList">
          <el-table-column label="ID">
            <template slot-scope="{ row }">
              <el-radio
                v-model="row.radio"
                :label="true"
                @input="selectId(row.id)"
                >{{ row.id }}</el-radio
              >
            </template>
          </el-table-column>
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
        </el-table>
        <el-pagination
          background
          @size-change="handleGoodSizeChange"
          @current-change="handleGoodCurrentChange"
          :page-sizes="[2, 5, 10, 20, 50, 100, 200]"
          :current-page="goodForm.pageNum"
          :page-size="goodForm.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="goodTotal"
        >
        </el-pagination>
        <div slot="footer">
          <el-button @click="selectGoodVisble = false">取消</el-button>
          <el-button type="primary" @click="selectGoodVisble = false"
            >确定</el-button
          >
        </div>
      </el-dialog>
    </el-dialog>
    <el-dialog title="修改视频" :visible.sync="updateDialogVisible">
      <el-form :model="updateForm" ref="upadteFormRef" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="updateForm.title" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="description" prop="description">
          <el-input
            type="textarea"
            :rows="2"
            v-model="updateForm.description"
            placeholder="请输入描述"
          ></el-input>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :action="videoAction"
            :on-success="uploadUpdateVideo"
            :headers="headers"
            name="file"
          >
            <el-button size="small" type="primary" icon="el-icon-upload"
              >点击上传</el-button
            >
            <div>
              <video class="video-upload"
              v-if="updateForm.videoUrl != null"
              :src="
                updateForm.videoUrl == null
                  ? null
                  : videoBaseUrl + updateForm.videoUrl
              "
              width="100%"
              controls
            ></video>
            </div>
            
          </el-upload>
        </el-form-item>
        <el-form-item label="审核">
          <el-switch
            active-value="1"
            inactive-value="0"
            v-model="updateForm.status"
          ></el-switch>
        </el-form-item>
        <el-form-item label="商品ID">
          <el-button type="primary" @click="getGoodList"
            >选择商品{{ updateForm.goodId == null ? null : ":" + updateForm.goodId }}
          </el-button>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="updateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateVideo"
          >确定</el-button
        >
      </div>
      <el-dialog
        title="选择商品"
        append-to-body
        :visible.sync="selectGoodVisble"
        show-overflow-tooltip
      >
        <el-table :data="goodList">
          <el-table-column label="ID">
            <template slot-scope="{ row }">
              <el-radio
                v-model="row.radio"
                :label="true"
                @input="selectId(row.id)"
                >{{ row.id }}</el-radio
              >
            </template>
          </el-table-column>
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
        </el-table>
        <el-pagination
          background
          @size-change="handleGoodSizeChange"
          @current-change="handleGoodCurrentChange"
          :page-sizes="[2, 5, 10, 20, 50, 100, 200]"
          :current-page="goodForm.pageNum"
          :page-size="goodForm.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="goodTotal"
        >
        </el-pagination>
        <div slot="footer">
          <el-button @click="selectGoodVisble = false">取消</el-button>
          <el-button type="primary" @click="selectGoodVisble = false">
            确定</el-button
          >
        </div>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
import { getVideos, getVideoById, deleteVideo, addVideo,updateVideo } from "@/api/video";
import { getImageUrl, getVideoUrl } from "@/api/url";
import { getGoods } from "@/api/good";
export default {
  name: "videos",
  data() {
    return {
      videoList: [],
      form: {
        pageNum: 1,
        pageSize: 10,
        query: "",
      },
      total: 0,
      addDialogVisible: false,
      imageUrl: getImageUrl(),
      videoBaseUrl: getVideoUrl(),
      url: null,
      showVisbleVideo: false,
      updateDialogVisible: false,
      videoUrl: null,
      headers: {
        token: window.sessionStorage.getItem("token"),
      },
      videoAction: "http://localhost:8081/upload/video",
      addForm: {
        title: null,
        description: null,
        status: null,
        goodId: null,
        coverUrl: null,
        videoUrl: null,
      },
      goodList: [],
      goodForm: {
        pageNum: 1,
        pageSize: 10,
        query: "",
      },
      goodTotal: 0,
      selectGoodVisble: false,
      updateForm: {},
    };
  },
  created() {
    this.getVideoList();
  },
  methods: {
    getVideoList() {
      getVideos(this.form).then((res) => {
        console.log(res);
        this.total = res.data.total;
        this.videoList = res.data.records;
      });
    },
    viewVideo(videoUrl) {
      this.url = videoUrl;
      this.showVisbleVideo = true;
    },
    getVideo(id) {
      getVideoById(id).then((res) => {
        console.log(res);
        this.updateForm = res.data;
        this.updateDialogVisible = true;
      });
    },
    async deleteVideo(id) {
      const confirmResult = await this.$confirm(
        "此操作将永久删除该视频, 是否继续?",
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
      const res = await deleteVideo(id);
      if (res.code !== 200) {
        return this.$message.error("删除视频失败！");
      }
      this.$message.success("删除成功！");
      this.getVideoList();
    },
    handleSizeChange(val) {
      this.form.pageSize = val;
      this.getVideoList();
    },
    handleCurrentChange(val) {
      this.form.pageNum = val;
      this.getVideoList();
    },
    handleGoodSizeChange(val) {
      this.goodForm.pageSize = val;
      this.getGoodList();
    },
    handleGoodCurrentChange() {
      this.goodForm.pageNum = val;
      this.getGoodList();
    },
    addVideo() {
      addVideo(this.addForm).then((res) => {
        console.log(res);
        this.addDialogVisible = false;
        this.$message.success("添加成功!");
        this.getVideoList();
      });
    },
    uploadVideo(res) {
      console.log(res);
      this.addForm.videoUrl = res.data.videoUrl;
      this.addForm.coverUrl = res.data.imageUrl;
    },
    uploadUpdateVideo(res) {
      this.updateForm.videoUrl = res.data.videoUrl;
      this.updateForm.coverUrl = res.data.imageUrl;
    },
    getGoodList() {
      getGoods(this.goodForm).then((res) => {
        this.goodList = res.data.records;
        this.goodTotal = res.data.total;
        this.goodList.map((item) => {
          this.$set(item, "radio", false);
        });
      });
      this.selectGoodVisble = true;
    },
    selectId(id) {
      if(this.addDialogVisible){
        this.addForm.goodId = id;
      }
      if(this.updateDialogVisible){
        this.updateForm.goodId=id;
      }
      this.goodList.map((item) => {
        if (item.id == id) {
          this.$set(item, "radio", true);
          item.radio = true;
        } else {
          this.$set(item, "radio", false);
        }
      });
    },
    updateVideo(){
      updateVideo(this.updateForm).then(res=>{
        console.log(res);
        this.updateDialogVisible=false;
        this.$message.success("更新成功！")
        this.getVideoList();
      })
    }
  },
};
</script>

<style scoped>
.video-upload{
  width: 300px;
  height: 300px;
}
</style>