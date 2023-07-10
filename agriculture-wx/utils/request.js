let axiosTime=0;
let token=wx.getStorageSync('token')||"";
export const http=(params)=>{
  return new Promise((resolve,reject)=>{
   axiosTime++;
   wx.showLoading({
     title: '加载中',
     mask:true
   })
    wx.request({
      ...params,
      header:{
        token:token
      },
      success:result=>{
        resolve(result)
      },
      fail:err=>{
        reject(err);
      },
      complete:()=>{
        axiosTime--;
        if(axiosTime==0)
        wx.hideLoading();
      }
    })
  })
}