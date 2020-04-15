package com.bdu.jiajiao.mapper;

import com.bdu.jiajiao.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 123
 * @version 1.0
 * @date 2020 2020/3/9 15:25
 */
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> queryByTeaName(String teacherName);

    List<Order> queryByStuName(String studentName);

    Order queryOrder(String teacherName,String studentName);

    void updateOrder(Order order);

    List<Order> queryOrderInfo();


}
