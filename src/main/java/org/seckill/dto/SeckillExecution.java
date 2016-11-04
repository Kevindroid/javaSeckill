package org.seckill.dto;

import org.seckill.entity.SuccessKilled;

/**
 * Created by 000015 on 2016/11/4.
 */
public class SeckillExecution {

    private long seckillId;
    //秒杀执行结果状态
    private int state;
    //状态表示
    private String stateInfo;
    //秒杀成功对象
    private SuccessKilled successKilled;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    /**
     * 秒杀成功后的构造器
     * @param seckillId
     * @param state
     * @param stateInfo
     * @param successKilled
     */
    public SeckillExecution(long seckillId, int state, String stateInfo, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.successKilled = successKilled;
    }

    /**
     * 秒杀失败后的构造器
     * @param seckillId
     * @param state
     * @param stateInfo
     */
    public SeckillExecution(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
