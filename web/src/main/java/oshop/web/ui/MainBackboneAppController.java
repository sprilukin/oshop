package oshop.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/app")
public class MainBackboneAppController {

    @RequestMapping(method = RequestMethod.GET)
    public String main() throws Exception {
        return "backbone/app";
    }
}