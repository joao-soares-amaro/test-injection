package com.amaro.ecp.testinjection.usecases

import com.amaro.ecp.testinjection.configs.AnotherConfig
import com.amaro.ecp.testinjection.configs.Config
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on

@ExtendWith(MockitoExtension::class)
internal class DoSomethingTest {

    private lateinit var doSomething: DoSomething

    @Mock
    lateinit var config: Config

    private val anotherConfig: AnotherConfig = mock() {
        on { value }.thenReturn("default anotherConfig value for all tests")
    }

    @BeforeEach
    fun setUp() {
        `when`(config.value).thenReturn("default config value for all tests")

        // esse cara tem que ser inicializado depois de todos os stubs
        doSomething = DoSomething(
            config,
            anotherConfig
        )
    }

    @Test
    fun `should do something`() {
        `when`(config.value).thenReturn("config value edited inside test")
        `when`(anotherConfig.value).thenReturn("anotherConfig value edited inside test")
        doSomething.doSomething()
        assertTrue(true)
    }
}