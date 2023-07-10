// index.js
import {http} from "../../utils/request"
Page({
  data: {
    baseUrl: "http://localhost:8080/image/",
    swiperList: [],
    typeRow1: [],
    typeRow2: [],
    hotProductList: [],
    openId: "",
    name:"",
    avatar:""
  },
  onLoad() {
    this.getCategory();
    this.getSwiper();
    this.getHotProductList();
    let that = this;
    wx.showModal({
      title: '登录提示',
      content: '获取用户头像',
      success: function (res) {
        if (res.confirm) { 
          that.getUserCode();
          that.getUserInfo();
        } else { 
          console.log('用户点击取消')
        }
      }
    })
  },

  login() {
    http({
      method:"POST",
      url:"http://localhost:8080/user/login",
      data:{
        name:this.data.name,
        avatar:this.data.avatar,
        openid:this.data.openId
      }
    }).then(res=>{
      try {
        wx.setStorage({
          key:"token",
          data:res.data.data
        })
      } catch (e) {
        console.log(e);
       }
    })
  },
  handlerJump(e){
    const id=e.currentTarget.dataset.id;
    const app=getApp();
    app.globalData.index=id;
    wx.switchTab({
      url: '/pages/category/index',
      success:(e)=>{
        var page = getCurrentPages().pop();
        if (page == undefined || page == null) return;
        page.onLoad();
      }
    })
  },
  getUserCode(){
    wx.login({
      success: (res) => {
        this.getOpenId(res.code)
      },
      
    })
  },
  getUserInfo() {
    wx.getUserProfile({
      desc: '获取用户名头像',
      success: res => {
        this.setData({
          name:res.userInfo.nickName,
          avatar:res.userInfo.avatarUrl,
        })
        this.login();
      }
    })
  },
  getOpenId(code) {
    wx.request({
      url: `https://api.weixin.qq.com/sns/jscode2session?appid=wxb402748dfff5d155&secret=d30612b3cdc63d181a6aae3a4bc97fa7&js_code=${code}&grant_type=authorization_code
      `,
      success: res => {
        this.setData({
          openId: res.data.openid
        });
      }
    })
  },
  getCategory() {
    wx.request({
      url: 'http://localhost:8080/category/menu',
      method: "GET",
      success: res => {
        let data = res.data.data.records;
        let row1 = data.filter((item, index) => {
          return index < 4;
        })
        let row2 = data.filter((item, index) => {
          return index >= 4;
        })
        this.setData({
          typeRow1: row1,
          typeRow2: row2
        });
      }
    })
  },
  getSwiper() {
    wx.request({
      url: 'http://localhost:8080/good/swiper',
      method: "GET",
      success: (res) => {
        this.setData({
          swiperList: res.data.data
        })
      }
    })
  },
  getHotProductList() {
    wx.request({
      url: 'http://localhost:8080/good/hot',
      method: "GET",
      success: (res) => {
        this.setData({
          hotProductList: res.data.data
        })
      }
    })
  },
})