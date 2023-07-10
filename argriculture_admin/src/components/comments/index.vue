<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>评论列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入内容"
            class="input-with-select"
            v-model="form.query"
            clearable
            @clear="getCommentList"
            @blur="getCommentList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getCommentList"
            ></el-button>
          </el-input>
        </el-col>
      </el-row>
      <el-table :data="commentList" border stripe style="width: 100%">
        <el-table-column label="ID" prop="id" width="180"></el-table-column>
        <el-table-column label="评论内容" prop="content"></el-table-column>
        <el-table-column label="评分">
          <template slot-scope="{ row }">
            <el-rate v-model="row.score" :colors="colors" disabled show-score>
            </el-rate>
          </template>
        </el-table-column>
        <el-table-column label="评论时间" prop="createTime"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="{ row }">
            <el-button type="danger" @click="deleteComment(row.id)" size="small"
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
  </div>
</template>

<script>
import { getComments ,deleteComment} from "@/api/comment";
export default {
  name: "comment",
  data() {
    return {
      commentList: [],
      form: {
        pageNum: 1,
        pageSize: 10,
        query: "",
      },
      total: 0,
      colors: ["#99A9BF", "#F7BA2A", "#FF9900"],
    };
  },
  created() {
    this.getCommentList();
  },
  methods: {
    getCommentList() {
      getComments(this.form).then((res) => {
        this.commentList = res.data.records;
        this.total = res.data.total;
      });
    },
    async deleteComment(id) {
      const confirmResult = await this.$confirm(
        "此操作将永久删除该评论, 是否继续?",
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
      const res = await deleteComment(id);
      if (res.code !== 200) {
        return this.$message.error("删除分类失败！");
      }
      this.$message.success("删除成功！");
      this.getCommentList();
    },
    handleSizeChange(val) {
      this.form.pageSize = val;
      this.getCommentList();
    },
    handleCurrentChange(val) {
      this.form.pageNum = val;
      this.getCommentList();
    },
  },
};
</script>