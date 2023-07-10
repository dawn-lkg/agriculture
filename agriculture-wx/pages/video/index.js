// pages/video/index.js
import {http} from "../../utils/request";
import {getRequestUrl} from "../../utils/common"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    videoList:[],
    pageNum:1,
    pageSize:8,
    query:"",
    url:"",
    total:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      url:getRequestUrl()+"image/"
    })
  },
  getVideo(){
    http({
      url:"http://localhost:8080/videos/list",
      method:"GET",
      data:{
        pageNum:this.data.pageNum,
        pageSize:this.data.pageSize
      }
    }).then(res=>{
      console.log(res);
      this.setData({
        videoList:res.data.data.records,
        total:res.data.data.total
      })
    })
  },
  addVideo(){
    let pageNum=this.data.pageNum;
    let pageSize=this.data.pageSize;
    let total=this.data.total;
    if(pageNum*pageSize>=total){
      return;
    }else{
    this.setData({
      pageNum:this.data.pageNum+1
    })
    http({
      url:"http://localhost:8080/videos/list",
      method:"GET",
      data:{
        pageNum:this.data.pageNum,
        pageSize:this.data.pageSize
      }
    }).then(res=>{
      let list=this.data.videoList.concat(res.data.data.records);
      this.setData({
        videoList:list
      })
    })
  }
  },
  onShow() {
    this.setData({
      pageNum:1,
      pageSize:8
    })
    this.getVideo();
  },
  onPullDownRefresh() {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    this.addVideo();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})