package com.feng.oauth.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feng.oauth.R.PcAccessToken;
import com.feng.oauth.service.AccessTokenService;
import com.feng.oauth.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: FengYang
 * @create: 2023-10-06 16:02
 **/
@Service
@Slf4j
public class AccessTokenServiceImpl implements AccessTokenService {
    @Value("${wx.appID}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;
    private String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    @Resource
    private RedisCache redisCache;
    @Override
    public PcAccessToken findPcToken() {
        String accessToken = redisCache.getCacheObject("resaccess_token");
        if (accessToken == null || accessToken.isEmpty()) {

            String res = HttpUtil.get(String.format(accessTokenUrl,appId,appSecret));
            JSONObject jsonObject = JSON.parseObject(res);
            log.info(res);
            String access_token = jsonObject.getString("access_token");
            redisCache.setCacheObject("resaccess_token", access_token, 1, TimeUnit.HOURS);
            accessToken = access_token;
        }
        PcAccessToken pcAccessToken = new PcAccessToken();
        pcAccessToken.setAccessToken(accessToken);
        return pcAccessToken;
    }
}