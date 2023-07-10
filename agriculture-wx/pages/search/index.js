// pages/search/index.js
import {http} from "../../utils/request"
import {getgetRequestUrl, getRequestUrl} from "../../utils/common"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderBy:1,
    low:"",
    height:"",
    showPopup:false,
    query:"",
    pageNum:1,
    pageSize:8,
    goodList:[],
    filter:false,
    isSale:false,
    total:0,
    baseImageUrl:getRequestUrl()+"/image/"
  },
  getGoods(){
    this.resSetPage();
    http({
      url:"http://localhost:8080/good/list/search",
      method:"GET",
      data:{
        pageNum:this.data.pageNum,
        pageSize:this.data.pageSize,
        query:this.data.query,
        isSale:this.data.isSale,
        low:this.data.low,
        height:this.data.height    
      }
    }).then(res=>{
      this.setData({
        goodList:res.data.data.records,
        total:res.data.data.total
      })
    })
  },
  setLow(e){
    this.setData({
      low:e.detail.value
    })
  },
  resSetPage(){
    this.setData({
      pageNum:1,
      pageSize:8
    })
  },
  setHeight(e){
    this.setData({
      height:e.detail.value
    })
  },
  setQuery(e){
    this.setData({
      query:e.detail.value
    })
  },
  clearQuery(){
    this.setData({
      query:""
    })
    this.getGoods();
  },
  showVisibleFun(){
    this.setData({
      showPopup:true
    })
  },
  resetValue(){
    this.setData({
      low:"",
      height:""
    })
  },
  confirmVale(){
    this.setData({
      showPopup:false
    })
    if(this.data.low!=null&&this.data.height!=null){
      this.setData({
        filter:true
      })
      
    }
    this.getGoods();
    
  },
  saleFun(){
    this.setData({
      isSale:true
    })
    this.getGoods();
  },
  changeSale(){
    this.setData({
      isSale:false
    })
    this.getGoods();
  },
  getList(){
    this.setData({
      pageNum:this.data.pageNum+1
    })
    http({
      url:"http://localhost:8080/good/list/search",
      method:"GET",
      data:{
        pageNum:this.data.pageNum,
        pageSize:this.data.pageSize,
        query:this.data.query,
        isSale:this.data.isSale,
        low:this.data.low,
        height:this.data.height    
      }
    }).then(res=>{
      this.setData({
        goodList:this.data.goodList.concat(res.data.data.records),
        total:res.data.data.total
      })
      console.log(this.data.goodList);
    })
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
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getGoods();
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
    if(this.data.pageNum*this.data.pageSize>this.data.total){
      return ;
    }
    this.getList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})