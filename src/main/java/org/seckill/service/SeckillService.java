package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by 000015 on 2016/11/4.
 * 站在使用者的角度上设计接口
 * 三个方面：方法定义粒度，参数(不要就是一个map)，返回类型（return 类型/异常）
 *
 */
public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀开启地址，否则输出系统时间和秒杀开始时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 执行秒杀操作通过存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProducer(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
