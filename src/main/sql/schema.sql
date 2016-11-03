CREATE database seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE table seckill(
  `seckill_id` bigint not null AUTO_INCREMENT COMMENT '商品库存ＩＤ',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)engine=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET =utf8 comment='秒杀库存表';

  -- 添加一些初始化数据
insert into seckill(name, number, start_time, end_time)
VALUES
  ('1000元秒杀iphone6',100,'2016-11-11 00:00:00','2016-11-12 00:00:00'),
  ('500元秒杀ipad',200,'2016-11-11 00:00:00','2016-11-12 00:00:00'),
  ('300元秒杀小米4',300,'2016-11-11 00:00:00','2016-11-12 00:00:00'),
  ('200元秒杀魅蓝手机',400,'2016-11-11 00:00:00','2016-11-12 00:00:00');


-- 秒杀成功明细表
-- 用户登陆认证的相关信息
CREATE TABLE success_killed(
 `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标识：-1：无效，0：成功，1：已付款，2：已发货，3：已收货',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),/*联合主键,同一个用户只能秒杀一款产品一次*/
  KEY idx_create_time(create_time)
)engine=InnoDB  DEFAULT CHARSET =utf8 comment='秒杀成功明细表';