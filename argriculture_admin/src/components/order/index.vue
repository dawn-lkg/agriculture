<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入内容"
            class="input-with-select"
            v-model="form.query"
            clearable
            @clear="getOrderList"
            @blur="getOrderList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getOrderList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加订单</el-button
          >
        </el-col>
      </el-row>
      <el-table :data="orderList" stripe border>
        <el-table-column label="ID" prop="id"></el-table-column>
        <el-table-column label="标题" prop="name"></el-table-column>
        <el-table-column label="描述" prop="description"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="数量" prop="quantity"></el-table-column>
        <el-table-column label="状态" prop="status">
          <template slot-scope="{ row }">
            <el-tag v-if="row.status == 0" type="">待付款</el-tag>
            <el-tag v-if="row.status == 1" type="success">待发货</el-tag>
            <el-tag v-if="row.status == 2" type="info">待收货</el-tag>
            <el-tag v-if="row.status == 3" type="info">待评价</el-tag>
            <el-tag v-if="row.status == 4" type="info">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="总价">
          <template slot-scope="{ row }">
            {{ row.price * row.quantity }}
          </template>
        </el-table-column>
        <el-table-column label="查看用户">
          <template slot-scope="{ row }">
            <el-button type="primary" @click="showUserFunction(row.userId)"
              >查看用户</el-button
            >
          </template>
        </el-table-column>
        <el-table-column label="查看收货地址">
          <template slot-scope="{ row }">
            <el-button type="primary" @click="showAddressFunction(row.addressId)">查看收货地址</el-button>
          </template>

          
        </el-table-column>
        <el-table-column label="下单时间">
          <template slot-scope="{ row }">
            {{ row.date }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" >
          <template slot-scope="{row}">
            <el-button type="success" @click="getOrder(row.id)">修改</el-button>
          <el-button type="danger" @click="deleteOrder(row.id)">删除</el-button>
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
    <el-dialog title="用户信息" :visible.sync="showUserVisible">
      <el-descriptions>
        <el-descriptions-item label="Id">{{
          userForm.id
        }}</el-descriptions-item>
        <el-descriptions-item label="用户名">{{
          userForm.name
        }}</el-descriptions-item>
        <el-descriptions-item label="余额">{{
          userForm.balance
        }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
    <el-dialog title="收货地址信息" :visible.sync="showAddressVisible">
      <el-descriptions>
        <el-descriptions-item label="Id">{{
          addressForm.id
        }}</el-descriptions-item>
        <el-descriptions-item label="收货人">{{
          addressForm.recipientName
        }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{
          addressForm.recipientPhone
        }}</el-descriptions-item>
        <el-descriptions-item label="收货地址">{{
          addressForm.recipientAddress
        }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
    <el-dialog
      title="修改状态"
      :visible.sync="updateDialogVisible"
      width="50%"
    >
    <el-select v-model="updateForm.status" placeholder="请选择">
    <el-option
      v-for="item in opetion"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
      <div slot="footer">
        <el-button @click="updateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateOrder">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getOrderById,
  getOrders,
  deleteOrder,
  addOrder,
  updateOrder,
} from "@/api/order";
import { getUserById } from "@/api/user";
import {getAddressById} from "@/api/address";
export default {
  name: "order",
  data() {
    return {
      orderList: [],
      form: {
        pageNum: 1,
        pageSize: 10,
        query: "",
      },
      total: 0, 
      showUserVisible: false,
      showAddressVisible:false,
      userForm: {},
      addressForm:{},
      updateForm:{},
      updateDialogVisible:false,
      opetion:[
        {
          value:0,
          label:"待付款"
        },
        {
          value:1,
          label:"待发货"
        },
        {
          value:2,
          label:"待收货"
        },
        {
          value:3,
          label:"待评价",
        },
        {
          value:4,
          label:"已完成"
        }
      ]
    };
  },
  created() {
    this.getOrderList();
  },
  methods: {
    getOrderList() {
      getOrders(this.form).then((res) => {
        console.log(res);
        this.orderList = res.data.records;
        this.total = res.data.total;
      });
    },
    handleSizeChange(val) {
      this.form.pageSize = val;
      this.getOrderList();
    },
    handleCurrentChange(val) {
      this.form.pageNum = val;
      this.getOrderList();
    },
    showUserFunction(val) {
      getUserById(val).then((res) => {
        this.userForm = res.data;
        this.showUserVisible = true;
      });
    },
    showAddressFunction(val){
      getAddressById(val).then((res) => {
        this.addressForm = res.data;
        this.showAddressVisible = true;
      });
    },
    async deleteOrder(id){
      const confirmResult = await this.$confirm(
        "此操作将永久删除该订单, 是否继续?",
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
      const res = await deleteOrder(id);
      if (res.code !== 200) {
        return this.$message.error("删除分类失败！");
      }
      this.$message.success("删除成功！");
      this.getOrderList();
    },
    async getOrder(id){
      const res=await getOrderById(id);
      this.updateForm=res.data;
      this.updateDialogVisible=true;
    },
    async updateOrder(){
      const res=await updateOrder(this.updateForm);
      this.updateDialogVisible=false;
      this.getOrderList();
      this.$message.success("更新状态成功！");
    }
  },
};
</script>

<style scoped>
</style>