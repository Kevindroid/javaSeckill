package org.seckill.exception;

/**
 * Created by 000015 on 2016/11/4.
 * 所有的秒杀相关异常
 */
public class SeckillException extends RuntimeException{

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
