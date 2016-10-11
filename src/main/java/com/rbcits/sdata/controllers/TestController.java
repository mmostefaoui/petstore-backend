package com.rbcits.sdata.controllers;

import com.rbcits.sdata.controllers.util.GenericResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public GenericResponse greeting(){
        return new GenericResponse("hello world");
    }
}
