/**
 * Created by Kevin on 2016/11/5.
 * 存放主要交互逻辑代码
 */
var seckill = {
    //封装秒杀相关ajax的url
    URL: {},
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //手机验证和登录，计时交互
            //规划交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');

            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            //验证手机号
            if (!seckill.validatePhone(killPhone)) {
                //绑定phone
                //控制输出
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false//关闭键盘事件
                });

                $("#killPhoneBtn").click(function () {
                    var input = $("#killphoneKey").val();
                    if (seckill.validatePhone(input)) {
                        //电话写入cookie
                        $.cookie('killPhone', input, {expires: 7, path: '/seckill'});
                        window.location.reload()
                    } else {
                        <!--先隐藏然后再赋值可以避免用户看到赋值过程中的内容-->
                        $('#killphoneMessage').hide().html('<label class="label-danger">手机号错误！</label>').show(300);
                    }

                });
            }
        }
    }
}