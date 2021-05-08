package com.school.oauth2.endpoint.service.impl;

import com.google.code.kaptcha.Producer;
import com.school.common.utils.RandomUtils;
import com.school.oauth2.endpoint.domain.Captcha;
import com.school.oauth2.endpoint.mapper.CaptchaMapper;
import com.school.oauth2.endpoint.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.util.Date;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private CaptchaMapper captchaMapper;
    @Autowired
    private Producer producer;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BufferedImage createCaptcha(String uuid) {
        Integer code = RandomUtils.getRandomInteger();
        Captcha captcha = new Captcha();
        captcha.setCode(code.toString());
        captcha.setUuid(uuid);
        //默认为3分钟
        Date date = new Date();
        long time = date.getTime();
        time += 300;
        date = new Date(time);

        captcha.setExpireTime(date);
        this.captchaMapper.insertSelective(captcha);
        return producer.createImage(code.toString());
    }

}
