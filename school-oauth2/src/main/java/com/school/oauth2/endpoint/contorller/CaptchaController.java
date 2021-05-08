package com.school.oauth2.endpoint.contorller;

import com.school.common.exception.CommonException;
import com.school.oauth2.endpoint.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Api("验证码接口")
@RestController
@RequestMapping("/public/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/captcha.jpg")
    @ApiOperation(value = "获取验证码照片")
    public void createCaptcha(HttpServletResponse response,String uuid) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setContentType("image/jpeg");

        if (StringUtils.isEmpty(uuid)) {
            throw new CommonException("验证码唯一ID不能为空");
        }

        try {
            BufferedImage captcha = this.captchaService.createCaptcha(uuid);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(captcha, "jpg", out);
            out.flush();
            IOUtils.closeQuietly(out);
        } catch (IOException e) {
            throw new CommonException("验证码获取失败" + e.getMessage());
        }
    }

}
