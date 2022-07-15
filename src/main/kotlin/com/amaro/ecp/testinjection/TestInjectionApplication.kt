package com.amaro.ecp.testinjection

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.amaro.ecp.testinjection")
class TestInjectionApplication

fun main(args: Array<String>) {
	runApplication<TestInjectionApplication>(*args)
}
