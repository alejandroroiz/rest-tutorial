package com.base22.rest.tutorial.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Api Info",
    tags = {"Api Info"})
public class HelloController {

  @GetMapping("/hello")
  @ApiOperation(value = "Get api greeting",
      tags = {"Api Info"})
  public String index() {
    return "Hello Base22. This is a message from Spring!";
  }
}
