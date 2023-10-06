package com.feng.oauth.controller;

import cn.hutool.http.HttpUtil;
import com.feng.oauth.R.PcAccessToken;
import com.feng.oauth.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: FengYang
 * @create: 2023-10-06 15:31
 **/
@RestController
@Slf4j
@RequestMapping("/auth")
public class LoginController {

    @GetMapping(value = "/login")
    public String login(){
        return "Hello World";
    }
}