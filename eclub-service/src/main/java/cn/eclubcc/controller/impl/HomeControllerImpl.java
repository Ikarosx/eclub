package cn.eclubcc.controller.impl;

import cn.eclubcc.controller.HomeController;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: markeNick
 */
@RestController
@RequestMapping("/home")
public class HomeControllerImpl implements HomeController {

    @Autowired
    private HomeService homeService;

    @Override
    @GetMapping("/clubList/{page}")
    public ResponseResult home(@PathVariable Integer page) {

        return homeService.queryClubListLimit(page, 10);
    }
}