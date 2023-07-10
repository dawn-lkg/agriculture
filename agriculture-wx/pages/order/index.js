// pages/order/index.js
import {http} from "../../utils/request"
import {getRequestUrl} from "../../utils/common";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    condition:true,
    orderList:[],
    status:"",
    url:""
  },
  getOrders(){
    http({
      methods:"GET",
      url:"http://localhost:8080/order/my",
      data:{
        status:this.data.status
      }
    }).then(res=>{
      this.setData({
        orderList:res.data.data
      })
      if(this.data.orderList.length!=0){
        this.setData({
          condition:false
        })
      }
    })
  },
  changeTabs(target){
    this.setData({
      status:target.detail.activeKey,
      condition:true
    })
    this.getOrders();
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      url:getRequestUrl()+"image/"
    })
    this.getOrders();
  },
  cacelOrder(e){
    const id=e.currentTarget.dataset.id;
    http({
      method:"delete",
      url:"http://localhost:8080/order/"+id
    }).then(res=>{
       if(res.data.data){
         this.getOrders();
       }
    })
  },
  confirmOrder(e){
    const id=e.target.dataset.id;
    http({
      method:"post",
      url:"http://localhost:8080/order/paid",
      data:{
        id:id
      }
    }).then(res=>{
      if(res.data.data){
        this.getOrders();
      }
    })
  },
  comfirmReceipt(e){
    const id=e.currentTarget.dataset.id;
    http({
      url:"http://localhost:8080/order/receipt",
      method:"PUT",
      data:{
        id:id
      }
    }).then(res=>{
      if(res.data.data){
        this.getOrders();
      }
    })
  },
  comment(e){  
    wx.navigateTo({
      url: '/pages/comment/index?id='+e.currentTarget.dataset.id,
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})