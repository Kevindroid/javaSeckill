package org.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by 000015 on 2016/11/4.
 */
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
