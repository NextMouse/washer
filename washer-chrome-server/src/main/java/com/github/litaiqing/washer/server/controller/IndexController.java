package com.github.litaiqing.washer.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/15 18:47
 * @since JDK 1.8
 */
@Controller
@ApiIgnore
public class IndexController {

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView(new RedirectView("/swagger-ui.html"));
    }

}
