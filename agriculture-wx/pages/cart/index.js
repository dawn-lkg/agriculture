// pages/cart/index.js
import {
  http
} from "../../utils/request";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    address: {},
    cartList: [],
    show: true,
    baseUrl: "http://localhost:8080/image/",
    deg: "",
    total:0,
    all:true,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },
  getAddress() {
    let addressId=wx.getStorageSync('addressId');
    http({
      method: "GET",
      url: "http://localhost:8080/address/"+addressId
    }).then(res => {
      this.setData({
        address: res.data.data
      })
    })
  },
  isSelectAll(){
    let cart=wx.getStorageSync('cart');
    for(let i=0;i<cart.length;i++){
      if(!cart[i].select){
        this.setData({
          all:false
        })
        return;
      }
    }
    this.setData({
      all:true
    })
  },
  getCart() {
    let cart = wx.getStorageSync('cart');
    if (cart) {
      this.setData({
        show: false
      })
      this.isSelectAll();
    }
    this.totalPrice();
    this.setData({
      cartList: cart
    })
  },
  numChange(e) {
    let id = e.target.dataset.id;
    let num = e.detail.count;
    console.log(num);
    let cart =wx.getStorageSync("cart");
    cart.forEach((item, index, arr) => {
      if (item.id == id) {
        if(num<=0){
          arr.splice(index,1);
        }else{
          arr[index].num = num;
        }
      }
    });
    wx.setStorageSync('cart', cart);
    if(num==0){
      this.getCart();
      return;
    }
    this.totalPrice();
  },
  checkChange(e) {
    let id = e.detail.value;
    this.setData({
      deg: id
    })
  },
  choose(e) {
    let id=e.target.dataset.id;
    let deg;
    if(this.data.deg.length>0){
      deg=true;
    }else{
      deg=false;
    }
    let cart=wx.getStorageSync("cart");
    cart.forEach((item,index,arr)=>{
      if(item.id==id){
        arr[index].select=deg;
      }
    })
    wx.setStorageSync('cart', cart)
    this.totalPrice();
    this.isSelectAll();
  },
  totalPrice(){
    let cart=wx.getStorageSync('cart');
    if(!cart){
      cart=[];
    }
    let total=0;
    cart.forEach(item=>{
      if(item.select){
        total+=item.price*item.num;
      }
    })
    this.setData({
      total:total
    })
  },
  selectAll(e){
    let deg=e.detail.value.length>0;
    let cart=wx.getStorageSync("cart");
    cart.forEach((item,index,arr)=>{
      if(deg){
        arr[index].select=true;
      }else{
        arr[index].select=false;
      }
    })
    this.setData({
      cartList:cart
    })
    wx.setStorageSync('cart', cart);
    this.totalPrice();
    
    
  },
  alterAddress() {
    wx.navigateTo({
      url: '/pages/selectAddress/index',
    })
  },
  acounts(){
    const cart=wx.getStorageSync('cart');
    let deg=false;
    cart.forEach(item=>{
      if(item.select){
        deg=true;
      }
    })
    if(!deg){
      wx.showToast({
        title: '未选中商品',
        icon:'error',
        mask:true
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/cartCharge/index',
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
    this.onLoad();
    this.getAddress();
    this.getCart();
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