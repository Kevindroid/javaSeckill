package org.seckill.dao;

import org.seckill.entity.SuccessKilled;

/**
 * Created by 000015 on 2016/11/3.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细,可过滤重复信息
     * @param seckillId
     * @param userPhone
     * @return 插入的结果及数量
     */
    int insertSuccessKilled(long seckillId,long userPhone);

    /**
     * 查询successkilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckillId);
}
