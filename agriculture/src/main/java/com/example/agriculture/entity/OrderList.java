package com.example.agriculture.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenliming
 * @date 2023/4/17 0:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderList {
    List<Orders> list;
    Double total;
}
