package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.omg.CORBA.TIMEOUT;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * Created by Kevin on 2016/11/6.
 */
public class RedisDao {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    public RedisDao(String ip,int port){
        jedisPool=new JedisPool(ip,port);
    }

    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId){
        //redis操作逻辑
        try {
            Jedis jd=jedisPool.getResource();

            try {
                String key="seckill:"+seckillId;
                //未实现内部序列化操作,get获取的是二进制数组byte[]-->反序列化-->Object(Seckill)
                //采用自定义序列化  protostuff:pojo
                byte[] bytes=jd.get(key.getBytes());
                if(bytes!=null){
                    Seckill seckill=schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    //seckill被反序列化
                    return seckill;
                }
            }finally {
                jd.close();
             }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        //set Object(Seckill)序列化-->byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;//1h
                String result = jedis.setex(key.getBytes(), timeout, bytes);//超时缓存
                return result;
            } finally {

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
 }
