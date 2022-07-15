package com.amaro.ecp.testinjection.configs

import org.springframework.context.annotation.Configuration

@Configuration
data class Config(
    val value: String = "default",
    val anotherConfig: AnotherConfig = AnotherConfig("default")
)

data class AnotherConfig(
    val value: String = "default"
)