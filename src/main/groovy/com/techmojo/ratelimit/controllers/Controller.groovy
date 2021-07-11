package com.techmojo.ratelimit.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping('/ratelimit')
    public ResponseEntity<String> process(){

        ResponseEntity.ok('success')

    }
}
