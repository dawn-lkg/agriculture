// pages/my/index.js
import {
  http
} from "../../utils/request"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    avatar: "",
    nickName: "",
    balance: "",
    token: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getUserInfo();
    try {
      var value = wx.getStorageSync('userInfo')
      if (value) {
        this.setData({
          avatar: value.avatar,
          nickName: value.name
        })
      }
    } catch (e) {
      console.log(e);
    }
  },
  getUserInfo() {
    http({
      method: "GET",
      url: "http://localhost:8080/user/info",
    }).then(res => {
      this.setData({
        nickName: res.data.data.name,
        avatar: res.data.data.avatar,
        balance: res.data.data.balance
      })
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
    this.getUserInfo();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {
    console.log(1);
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