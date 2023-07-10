import Vue from 'vue'
import VueRouter from 'vue-router'
import good from "@/components/good/index.vue"
import category from "@/components/category/index.vue"
import user from "@/components/user/index.vue"
import admin from "@/components/admin/index.vue"
import video from "@/components/video/index.vue"
import order from "@/components/order/index.vue"
import echart from "@/components/echart/index.vue"
import comment from "@/components/comments/index.vue";
import home from "@/views/home//index.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'main',
    redirect:"/login"
  },
  {
    path: '/login',
    name: 'login',
    component: ()=>import("../views/login/index.vue")
  },
  {
    path: '/home',
    name: 'home',
    component:home,
    redirect: "/good",
    children:[
      {
        path: '/good',
        name: 'good',
        component:good
      },
      {
        path: '/category',
        name: 'category',
        component:category
      },
      {
        path: '/user',
        name: 'user',
        component:user
      },
      {
        path: "/admin",
        name:"admin",
        component:admin
      },
      {
        path: "/video",
        name:"video",
        component:video
      },
      {
        path: "/order",
        name:"order",
        component:order
      },
      {
        path: "/echart",
        name:"echart",
        component:echart
      },
      {
        path:"/comment",
        name:"comment",
        component:comment
      }
    ]
    
  },{
    path: '/good',
    name: 'good',
    component:()=>import('@/components/good/index.vue')
    
  },{
    path:"/comment",
    name: 'comment',
    comment:()=>import('@/components/comments/index.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
