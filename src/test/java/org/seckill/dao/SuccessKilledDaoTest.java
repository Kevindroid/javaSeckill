package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by 000015 on 2016/11/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})

public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        int result=successKilledDao.insertSuccessKilled(1000L,18014786682L,0);
        System.out.println("插入结果: "+result);//如果两次插入相同的值会不成功，返回0
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        Long seckillId=1000l;
        Long userPhone=18552137630l;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println("查询结果："+successKilled);
    }

}