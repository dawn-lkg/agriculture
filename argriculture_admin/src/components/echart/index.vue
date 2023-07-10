<template>
  <div class="echat">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据分析</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <div class="echarts">
        <div class="pie" style="width: 400px; height: 500px"></div>
      <div class="bar" style="width: 400px; height: 500px"></div>
      </div>
      
    </el-card>
  </div>
</template>

<script>
import { getPie } from "@/api/echarts";
export default {
  name: "echat",
  data() {
    return {
      chart: [],
    };
  },
  created() {},
  async mounted() {
    const res=await getPie();
    this.chart=res.data;
    console.log(this.chart);
    this.drawPie();
    this.drawBar();
  },

  methods: {
    
    drawBar() {
      let myChart = this.$echarts.init(document.querySelector(".bar"));
      const name=this.chart.map(item=>{
        return item.name;
      })
      const value=this.chart.map(item=>{
        return item.value;
      })
      console.log(name);
      let option = {
        xAxis: {
          type: "category",
          data: name,
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: value,
            type: "bar",
            showBackground: true,
            backgroundStyle: {
              color: "rgba(180, 180, 180, 0.2)",
            },
          },
        ],
      };
      myChart.setOption(option);
    },
    drawPie() {
      let myChart = this.$echarts.init(document.querySelector(".pie"));
      let option = {
        title: {
          text: "农产品销量图",
          subtext: "Fake Data",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "Access From",
            type: "pie",
            radius: "50%",
            data: this.chart,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  },
};
</script>

<style scoped>
.echarts{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}
</style>