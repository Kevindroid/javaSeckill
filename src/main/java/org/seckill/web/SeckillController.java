package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 2016/11/5.
 */
@Controller
@RequestMapping(value="/seckill")//url:/模块/资源/{id}/细分
public class SeckillController {

    @Resource
    private SeckillService seckillService;
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        //model渲染数据list.jsp+model=ModelAndView
        List<Seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(Model model,@PathVariable("seckillId") Long seckillId){
        if(seckillId==null){
            return "redirect:/seckill/list";//当seckillId不存在的时候，把请求重定向到list这个请求上
        }
        Seckill seckill=seckillService.getById(seckillId);
        if(seckill==null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    //ajax接口返回类型是json，设置produces解决json中的乱码问题
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    public @ResponseBody SeckillResult<Exposer> exposer(Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer=seckillService.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result=new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;

    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,
                produces = {"application/json;charset=UTF-8"})
    public @ResponseBody SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
             @PathVariable("md5") String md5, @CookieValue(value = "killPhone",required = false) Long phone){
        if(phone==null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        //SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution execution=seckillService.executeSeckill(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch (RepeatKillException e){
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false,execution);
        }catch (SeckillCloseException e){
            SeckillExecution execution=new SeckillExecution(seckillId,SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(false,execution);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecution execution=new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false,execution);
        }

    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    public SeckillResult<Long> time(){
        Date now=new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
