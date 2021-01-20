package com.zyw.jwt.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping
public class LoginController {


    @GetMapping("/get/token/{username}")
    public String getToken(@PathVariable("username") String username){
        Map<String,Object> infoMap = new HashMap<>(16);
        infoMap.put("userId",46);
        infoMap.put("name",username);
        infoMap.put("salary",1000000);
        String token = Jwts.builder().setSubject("zyw").setExpiration(new Date(System.currentTimeMillis() + 28800000))
                .setClaims(infoMap).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "jwtDemo").compact();
        return token;

    }

    @GetMapping("/parse/token/{token}")
    public String parseToken(@PathVariable("token") String token){
        Claims claims = Jwts.parser().setSigningKey("jwtDemo").parseClaimsJws(token).getBody();
        String name = (String)claims.get("name");
        String salary = String.valueOf(claims.get("salary"));
        String userId = String.valueOf(claims.get("userId"));
        log.info(name);
        log.info(salary);
        log.info(userId);
        return name;
    }
}
