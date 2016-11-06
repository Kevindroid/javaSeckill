package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by luyun on 2016/11/5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);//打印时将list放置于占位符中
    }

    @Test
    public void getById() throws Exception {
        long id = 1;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long id=1;
        long phone=1801478662L;
        String md5="66fa8394a2ce7b4a6b56563320677240";
        try {
            SeckillExecution execution=seckillService.executeSeckill(id,phone,md5);
            logger.info("execution={}",execution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage());
        }

    }

    @Test
    /**
     * 将判断秒杀是否开启和执行秒杀合并测试，可以重复执行
     */
    public void testSeckillLogic() throws Exception{
        long id=2l;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}", exposer);
            long phone=1801478662L;
            String md5=exposer.getMd5();
            try {
                SeckillExecution execution=seckillService.executeSeckill(id,phone,md5);
                logger.info("execution={}",execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }
        }else {
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void executeSeckillProcedure(){
        long seckillId=3l;
        long phone=55555678910l;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution execution=seckillService.executeSeckillProducer(seckillId,phone,md5);
            logger.info(execution.getStateInfo());
        }
    }

}