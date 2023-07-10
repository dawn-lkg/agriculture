// pages/addAddress/index.js
import {
  http
} from "../../utils/request";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name: "",
    phone: "",
    address: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },
  addAddress() {
    http({
      method: "POST",
      url: "http://localhost:8080/address/add",
      data: {
        recipientName: this.data.name,
        recipientPhone: this.data.phone,
        recipientAddress: this.data.address
      }
    }).then(res => {
      console.log(res);
      if (res.data.data) {
        wx.navigateBack({
          delta: 1,
          success: e => {
            var page = getCurrentPages().pop();
            if (page == undefined || page == null) return;
            page.onLoad();
          }
        });
      }
    })
  },
  setName(e) {
    this.setData({
      name: e.detail.value
    })
  },
  setPhone(e) {
    this.setData({
      phone: e.detail.value
    })
  },
  setAddress(e) {
    this.setData({
      address: e.detail.value
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