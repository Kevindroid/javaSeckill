package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by luyun on 2016/11/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    @Resource
    private RedisDao redisDao;
    @Resource
    private SeckillDao seckillDao;

    private long id=1l;

    @Test
    public void testSeckill() throws Exception {
        //get and set
        Seckill seckill=redisDao.getSeckill(id);
        if(seckill==null){
            seckill=seckillDao.queryById(id);
            if(seckill!=null){
                String result=redisDao.putSeckill(seckill);
                System.out.println("result结果为 "+result);
                seckill=redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }



}