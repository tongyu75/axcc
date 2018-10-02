package com.axcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @RequestMapping(value="/hello",produces="text/html;charset=UTF-8",method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }

    @RequestMapping(value="/address/{id}",method = RequestMethod.GET)
    public Map<String,Object> address(@PathVariable long id){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("cdoe", id);
        return result;
    }
}
