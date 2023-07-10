// pages/category/Catindex.js
import {http} from "../../utils/request";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    Catindex:-1,
    baseUrl:"http://localhost:8080/image/",
    goods:[],
    categorys:[],
    pageNum:1,
    pageSize:8,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    this.setIndex();
    this.getCategorys();
    this.getGoods();
    
  },
  onShow(){
    
  },
  setIndex(){
    const app=getApp();
    this.setData({
      Catindex:app.globalData.index
    })
  },
  getCategorys(){
    wx.request({
      url: 'http://localhost:8080/category/all',
      method:"GET",
      success:(res)=>{
        this.setData({
          categorys:res.data.data,
        })
      }
    })
  },
  getGoods(){
    http({
      url:"http://localhost:8080/good/type",
      method:"GET",
      data:{
        id:this.data.Catindex,
         pageNum:this.data.pageNum,
         pageSize:this.data.pageSize
      }
    }).then(res=>{
      this.setData({
          goods:res.data.data.records
        })
    });
  },
  change(e){
    this.setData({
      goods:[]
    })
    let index=e.currentTarget.dataset.value;
    this.setData({
      Catindex:index
    })
    this.getGoods();
  },
  addCart(e){
    let cart=wx.getStorageSync('cart')||[];
    let good=e.target.dataset.good;
    let index=cart.findIndex(v=>v.id===good.id);
    if(index==-1){
      good.num=1;
      good.select=false;
      cart.push(good);
    }else{
      cart[index].num++;
    }
    wx.setStorageSync('cart', cart);
    wx.showToast({
      title: '加入成功',
      icon:'success',
      mask:true
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {
    getApp().globalData.index=this.data.Catindex;
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