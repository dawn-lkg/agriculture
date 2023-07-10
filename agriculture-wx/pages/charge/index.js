import {
  http
} from "../../utils/request";
import {
  getRequestUrl
} from "../../utils/common"

// pages/charge/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    addressId: null,
    address: "",
    goodId: "",
    good: "",
    baseImageUrl: getRequestUrl() + "image/",
    num: 1,

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad({
    id
  }) {
    this.setData({
      goodId: id
    });
    this.getGood();
  },
  getAddress() {
    let id = wx.getStorageSync("addressId");
    this.setData({
      addressId: id
    })
    if (!this.data.addressId) {
      this.setData({
        addressId: null
      })
      return;
    }
    http({
      url: "http://localhost:8080/address/" + this.data.addressId
    }).then(res => {
      this.setData({
        address: res.data.data
      })
    })
  },
  getGood() {
    http({
      url: "http://localhost:8080/good/" + this.data.goodId,
    }).then(res => {
      this.setData({
        good: res.data.data
      })
    })
  },
  setNum(e) {
    this.setData({
      num: e.detail.count
    })
  },
  submitOrder() {
    if (this.data.addressId == "") {
      wx.showToast({
        title: '地址不能为空',
        icon: 'error',
        mask: true
      })
      return;
    }

    http({
      url: "http://localhost:8080/order/add",
      method: "POST",
      data: {
        goodId: this.data.goodId,
        addressId: this.data.addressId,
        quantity: this.data.num
      }
    }).then(res => {
      if (!res.data.data) return;
      console.log(res);
      wx.showToast({
        title: '添加订单成功',
        icon: 'success',
        mask: true
      })
      wx.navigateTo({
        url: '/pages/paid/index?id=' + res.data.data,
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