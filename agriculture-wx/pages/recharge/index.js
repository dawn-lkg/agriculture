// pages/recharge/index.js
import {http}  from "../../utils/request"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    chargeRule:[{
      require:true
    },{
      type:'number',
      min:1,
      max:10,
      message:"必须为数字"
    }],
    value:"",
  },
  inputCharge(e){
    this.setData({
      value:e.detail.value
    })
  },
  recharge(){
    http({
      method:"POST",
      url:"http://localhost:8080/user/recharge",
      data:{
        balance:this.data.value
      }
    }).then(res=>{
      if(res.data.data){
        wx.switchTab({
          url: '/pages/my/index',
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    
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