// pages/videoPlay/index.js
import {http} from "../../utils/request"
import {getRequestUrl} from "../../utils/common"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    info:{

    },
    videoId:null,
    baseurl:"",
    showComment:false,
    commentList:[],
    content:null,
    commentNum:0,
    likeNum:0,
    isLike:null,
    imageBaseUrl:getRequestUrl()+"/image/",
    focus:false,
    parentCommentId:""
  },
  setContent(e){
    this.setData({
      content:e.detail.value
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad({id}) {
    this.setData({
      baseurl:getRequestUrl()+"video/",
      videoId:id
    })
    this.getIsLike();
    this.getVideo(id);
    this.getCommentNum();
    this.getLikeNum();
    
  },
  getVideo(id){
    http({
      url:"http://localhost:8080/videos/"+id,
      method:"GET"
    }).then(res=>{
      
      this.setData({
        info:res.data.data
      })
    })
  },
  getComments(){
    http({
      url:"http://localhost:8080/videocomment/"+this.data.videoId,
    }).then(res=>{
      this.setData({
        commentList:res.data.data
      })
      this.data.commentList.map(item=>{
        item.deg=false;
      })
      //console.log(this.data.commentList);
    })
  },
  showCommentFun(){
    this.getComments();
      this.setData({
        showComment:true
      })
  },
  sendcomment(){
    http({
      url:"http://localhost:8080/videocomment/addComment",
      method:"POST",
      data:{
        videoId:this.data.videoId,
        content:this.data.content,
        parentCommentId:this.data.parentCommentId
      }
    }).then(res=>{
      this.getComments();
      this.setData({
        content:null
      })
      this.getCommentNum();
    })
  },
  getCommentNum(){
    http({
      url:"http://localhost:8080/videocomment/count",
      method:"GET",
      data:{
        videoId:this.data.videoId
      }
    }).then(res=>{
      this.setData({
        commentNum:res.data.data
      })
      //console.log(res);
    })
  },
  getLikeNum(){
    http({
      url:"http://localhost:8080/videolike/count",
      method:"GET",
      data:{
        videoId:this.data.videoId
      }
    }).then(res=>{
      this.setData({
        likeNum:res.data.data
      })
    })
  },
  getIsLike(){
    http({
      url:"http://localhost:8080/videolike/islike",
      method:"GET",
      data:{
        videoId:this.data.videoId
      }
    }).then(res=>{
      this.setData({
        isLike:res.data.data
      })
     
    })
  },
  updateLike(){
    http({
      url:"http://localhost:8080/videolike/update",
      method:"PUT",
      data:{
        videoId:this.data.videoId
      }
    }).then(res=>{
      //console.log(res);
      if(res.data.data){
        this.getIsLike();
        this.getLikeNum();
      }
    })
  },
  openChildren(e){
    let id=e.currentTarget.dataset.id;
    let obj=this.data.commentList.map(item=>{
      if(item.id==id){
        item.deg=!item.deg;
      }
      return item;
    })
    //console.log(obj);
    this.setData({
      commentList:obj
    })
  },
  reply(e){
    let id=e.currentTarget.dataset.id;
    if(id==this.data.parentCommentId){
      console.log(1223);
      this.setData({
        parentCommentId:""
      })
      return;
    }
    this.setData({
      focus:true,
      parentCommentId:id
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