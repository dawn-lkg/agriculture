// pages/good/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    good:{},
    baseUrl:"http://localhost:8080/image/",
  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad({id}) {
    
    this.getGood(id);
  },
  getGood(id){
    wx.request({
      url: 'http://localhost:8080/good/'+id,
      method:"GET",
      success:res=>{
        this.setData({
          good:res.data.data
        })
      }
    })
  },
  addCart(){
    let cart=wx.getStorageSync('cart')||[];
    let good=this.data.good;
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
  toBuy(e){
    let id=e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/charge/index?id='+id,
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