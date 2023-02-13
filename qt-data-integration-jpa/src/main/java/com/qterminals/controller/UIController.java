package com.qterminals.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UIController {
    @RequestMapping(value = { "/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/***/{y:[\\w\\-]+}" })
    public ModelAndView indexRoutes(HttpServletRequest request) {
        return new ModelAndView("/index.html");
    }

    @RequestMapping(value = { "/data-integration/", "/data-integration/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/***/{y:[\\w\\-]+}" })
    public ModelAndView dataIntegrationRoutes(HttpServletRequest request) {
        return new ModelAndView("/index.html");
    }
}
