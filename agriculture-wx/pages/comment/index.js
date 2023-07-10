// pages/comment/index.js
import {http} from "../../utils/request";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    score:0,
    content:"",
    orderId:"",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad({id}) {
    console.log(id);
    this.setData({
      orderId:id
    })
  },
  setScore(e){
    this.setData({
      score:e.detail.score
    })
  },
  setContent(e){
     this.setData({
       content:e.detail.value
     })
  },
  submitComment(){
    http({
      url:"http://localhost:8080/comment/add",
      method:"POST",
      data:{
        score:this.data.score,
        content:this.data.content,
        orderId:this.data.orderId
      }
    }).then(res=>{
      if(res.data.data){
        wx.navigateBack({
          delta: 1
        });      
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