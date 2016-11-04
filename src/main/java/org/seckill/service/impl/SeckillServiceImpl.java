package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 2016/11/4.
 */
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private SeckillDao seckillDao;
    @Resource
    private SuccessKilledDao successKilledDao;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    //混淆盐值，不希望用户猜到结果
    private final String slat="aslkdjfais(*&)*&^yaskemfg3412357*(&Y*TR$^&%(*&HNJKBFDK<LO?><LJ";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,100);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill=seckillDao.queryById(seckillId);
        if(seckill==null){
            return  new Exposer(false,seckillId);
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转换特定字符串的过程，不可逆
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        return null;
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+slat;//通过盐值和规则混淆生成具体的md5值
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}