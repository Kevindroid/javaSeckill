package org.seckill.exception;

/**
 * Created by 000015 on 2016/11/4.
 * 重复秒杀异常,Spring的声明式事务只会在抛出运行期异常才会回滚
 */
public class RepeatKillException  extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

}
