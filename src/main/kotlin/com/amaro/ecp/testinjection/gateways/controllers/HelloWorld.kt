package com.amaro.ecp.testinjection.gateways.controllers

import com.amaro.ecp.testinjection.usecases.DoSomething
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
data class HelloWorld(
    val doSomething: DoSomething,
) {

    val lambda = {
        doSomething.anotherConfig
    }

    @GetMapping("hello")
    @ResponseStatus(HttpStatus.OK)
    fun hello(): String {
        println(lambda().value)
        return "Hi! I am on air."
    }
}