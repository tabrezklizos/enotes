package com.tab.EnoteApp.controller;

import com.tab.EnoteApp.service.HomeService;
import com.tab.EnoteApp.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?>verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        Boolean savedUser = homeService.verifyAccount(uid, code);
        if(savedUser) {
            return CommonUtil.createResponseMessage("verified", HttpStatus.OK);
        }
        return CommonUtil.createResponseMessage("error in verification code",HttpStatus.BAD_REQUEST);
    }

}
