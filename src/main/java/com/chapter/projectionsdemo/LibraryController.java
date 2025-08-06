package com.chapter.projectionsdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class LibraryController{


  @GetMapping("/hello")
  public ResponseEntity<String> getHello(){
    return ResponseEntity.ok("Hello from LibraryController");
  }

}
