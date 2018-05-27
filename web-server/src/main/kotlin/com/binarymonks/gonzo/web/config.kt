package com.binarymonks.gonzo.web

import com.binarymonks.gonzo.core.users.service.SignInService
import com.binarymonks.gonzo.web.filters.JWTAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@PropertySource("classpath:db.properties")
class GonzoDataConfig {

    @Autowired
    lateinit var env: Environment

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName")!!)
        dataSource.url = env.getProperty("jdbc.url")
        dataSource.username = env.getProperty("jdbc.user")
        dataSource.password = env.getProperty("jdbc.pass")

        return dataSource
    }
}

class Correct4XXEntryPoint : AuthenticationEntryPoint {

    /**
     * 401 for Authentication errors and 403 for Authorization errors
     */
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, arg2: AuthenticationException) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not Authentic")
    }
}

@Configuration
@EnableWebSecurity
class GonzoSecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var signInService:SignInService

    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()
                ?.antMatchers(HttpMethod.GET,"/**")
                ?.permitAll()
                ?.and()
                ?.authorizeRequests()
                ?.antMatchers(HttpMethod.POST,"/**")
                ?.authenticated()
                ?.and()
                ?.authorizeRequests()
                ?.antMatchers(HttpMethod.PUT,"/**")
                ?.authenticated()
                ?.and()
                ?.addFilter(JWTAuthenticationFilter(
                        signInService,
                        authenticationManager()
                ))
        http?.cors()?.and()?.csrf()?.disable()
        http?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http?.exceptionHandling()?.authenticationEntryPoint(Correct4XXEntryPoint())
    }
}