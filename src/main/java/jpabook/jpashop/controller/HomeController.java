package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {
    //Slf4j를 사용하면 아래 로직이 생긴 것 과 같음
    //Logger log = LoggerFactory.getILoggerFactory(getClass());
    @RequestMapping("/")
    public String home(){
        log.info("home controller");
        return "home";
    }
}

