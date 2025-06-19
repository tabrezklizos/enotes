package com.tab.EnoteApp.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface HomeService {

   public Boolean verifyAccount(@RequestParam Integer uid, @RequestParam String verificationCode) throws Exception;


}
