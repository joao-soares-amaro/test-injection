package com.amaro.ecp.testinjection.usecases

import com.amaro.ecp.testinjection.configs.AnotherConfig
import com.amaro.ecp.testinjection.configs.Config
import org.springframework.stereotype.Service

@Service
data class DoSomething(
    val config: Config,
    val anotherConfig: AnotherConfig,
) {

    private val insideConfigValue = config.value
    private val insideAnotherConfigValue = anotherConfig.value

    init {
        println("1 config.value: ${config.value} from init")
        println("2 anotherConfig.value: ${anotherConfig.value} from init")
        println("3 insideConfigValue: $insideConfigValue from init")
        println("4 insideAnotherConfigValue: $insideAnotherConfigValue from init")
    }

    fun doSomething() {
        println("5 config.value: ${config.value} from doSomething")
        println("6 anotherConfig.value: ${anotherConfig.value} from doSomething")
        println("7 insideConfigValue: $insideConfigValue from doSomething")
        println("8 insideAnotherConfigValue: $insideAnotherConfigValue from doSomething")
    }
}