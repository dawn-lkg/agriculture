// pages/address/index.js
import {http} from "../../utils/request";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    addressList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    
  },
  getAddress(){
    http({
      url:"http://localhost:8080/address/getAll",
    }).then(res=>{
      this.setData({
        addressList:res.data.data
      })
    })
  },
  setDefault(e){
    const id=e.detail.value;
    http({
      method:"put",
      url:"http://localhost:8080/address/default",
      data:{
        id:id
      }
    }).then(res=>{
      if(res.data.data){
        this.getAddress();
      }
      
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
    this.getAddress();
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