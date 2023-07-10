package com.example.agriculture;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.agriculture.constants.SystemConstants;
import com.example.agriculture.dao.OrderDao;
import com.example.agriculture.entity.Admin;
import com.example.agriculture.entity.Shippingaddress;
import com.example.agriculture.entity.Videos;
import com.example.agriculture.entity.vo.OrderVo;
import com.example.agriculture.entity.vo.VideoVo;
import com.example.agriculture.service.AdminService;
import com.example.agriculture.service.OrderService;
import com.example.agriculture.service.ShippingaddressService;
import com.example.agriculture.service.VideosService;
import com.example.agriculture.utils.JwtTokenUtil;
import com.example.agriculture.utils.VideoCoverUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class AgricultureApplicationTests {
    @Resource
    OrderDao orderDao;
    @Autowired
    ShippingaddressService shippingaddressService;
    @Test
    void contextLoads() {
        System.out.println(orderDao.userOrder(0, 1));
        System.out.println(orderDao.userOrderAll(1));
    }
    @Test
    void test(){
        String s = JwtTokenUtil.generateToken("1");
        System.out.println(s);
        System.out.println(JwtTokenUtil.parseToken(s));
    }
    @Test
    void test1(){
        LambdaQueryWrapper<Shippingaddress> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shippingaddress::getUserId,6);
        Shippingaddress shippingaddress = new Shippingaddress();
        //shippingaddress.setUserId(6);
        shippingaddress.setIsDefault(SystemConstants.ADDRESS_NOT_DEFAULT);
        shippingaddressService.update(shippingaddress,lambdaQueryWrapper);

    }
    @Test
    public void test2(){
        String s= VideoCoverUtil.getVideoCover("E:/video/test.mp4","E:/video/");
        System.out.println(s);
    }
    @Autowired
    OrderService orderService;
    @Autowired
    VideosService videosService;
    @Autowired
    AdminService adminService;
    @Test
    public void test3(){
        VideoVo videoVo=new VideoVo();
        Videos byId = videosService.getById(1);
        Admin byId1 = adminService.getById(1);
        BeanUtils.copyProperties(byId,videoVo);
        System.out.println(videoVo);
        System.out.println(byId1);

    }
    @Test
    public void test4(){
        List<OrderVo> orderVos = orderService.userOrderAll(6);
        System.out.println(orderVos);
    }
}
