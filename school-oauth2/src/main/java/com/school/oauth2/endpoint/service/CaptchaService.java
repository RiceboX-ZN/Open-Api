package com.school.oauth2.endpoint.service;

import java.awt.image.BufferedImage;

public interface CaptchaService {
    BufferedImage createCaptcha(String uuid);
}
