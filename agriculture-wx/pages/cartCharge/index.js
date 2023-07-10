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
    good: "",
    baseImageUrl: getRequestUrl() + "image/",
    orderList:[],
    total:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    this.getOrders();
    this.getTotal();
  },
  getOrders(){
    let orders=wx.getStorageSync('cart');
    let list=orders.filter(item=>{
      if(item.select){
        return item;
      }
    })
    this.setData({
      orderList:list
    })
  },
  getTotal(){
    let total=0;
    this.data.orderList.forEach(item=>{
      total+=item.price*item.num;
    })
    this.setData({
      total:total
    })
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
  submitOrder() {
    if (this.data.addressId == "") {
      wx.showToast({
        title: '地址不能为空',
        icon: 'error',
        mask: true
      })
      return;
    }
    let list=this.data.orderList.map(item=>{
      let obj={};
      obj.goodId=item.id;
      obj.quantity=item.num;
      obj.addressId=this.data.addressId;
      return obj;
    });
    console.log(list);
    http({
      url:"http://localhost:8080/order/addList",
      method:"POST",
      data:{
        list,
        total:this.data.total
      }
    }).then(res=>{
      if(!res.data.data){
        wx.showToast({
          title: res.data.msg,
          icon: 'error',
          mask: true
        })
        return;
      }
      let cart=wx.getStorageSync('cart');
      let result=cart.filter(item=>{
        if(!item.select){
          return item;
        }
      })
      wx.setStorageSync('cart', result);
      wx.showToast({
        title: "购买成功",
        icon: 'success',
        mask: true
      })
      wx.navigateBack()({
        delta:1
      })
    })
    // http({
    //   url: "http://localhost:8080/order/add",
    //   method: "POST",
    //   data: {
    //     goodId: this.data.goodId,
    //     addressId: this.data.addressId,
    //     quantity: this.data.num
    //   }
    // }).then(res => {
    //   if (!res.data.data) return;
    //   console.log(res);
    //   wx.showToast({
    //     title: '添加订单成功',
    //     icon: 'success',
    //     mask: true
    //   })
    //   wx.navigateTo({
    //     url: '/pages/paid/index?id=' + res.data.data,
    //   })
    // })

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