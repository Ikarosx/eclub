package cn.eclubcc.controller.impl;

import cn.eclubcc.controller.HomeController;
import cn.eclubcc.pojo.http.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: markeNick
 */
@RestController
@RequestMapping("/home")
public class HomeControllerImpl implements HomeController {



    @Override
    @GetMapping("/index")
    public ResponseResult home() {
        return null;
    }
}