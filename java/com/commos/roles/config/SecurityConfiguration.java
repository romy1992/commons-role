package com.commos.roles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  protected static final String USER = "USER";
  protected static final String ADMIN = "ADMIN";
  protected static final String SUPER_ADMIN = "SUPER_ADMIN";

  protected static final String[] USER_MATCHER = {"/api/save/"};
  protected static final String[] USER_ALL = {"/api/confirm-account"};
  protected static final String[] ADMIN_MATCHER = {"/api/getAll/"};
  protected static final String[] SUPER_ADMIN_MATCHER = {"/api/update/"};

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String realm = "REAME";
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(USER_ALL)
        .permitAll()
        .antMatchers(USER_MATCHER)
        .hasAnyRole(USER)
        .antMatchers(ADMIN_MATCHER)
        .hasAnyRole(ADMIN)
        .antMatchers(SUPER_ADMIN_MATCHER)
        .hasAnyRole(SUPER_ADMIN)
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .realmName(realm)
        .authenticationEntryPoint(getBasicAuthEntryPoint())
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  public AuthEntryPoint getBasicAuthEntryPoint() {
    return new AuthEntryPoint();
  }

  /* To allow Pre-flight [OPTIONS] request from browser */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    UserBuilder users = User.builder();

    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

    manager.createUser(
        users
            .username("FoODy_NeT_UsEr")
            .password(new BCryptPasswordEncoder().encode("FoODy_NeT_UsEr"))
            .roles(USER)
            .build());

    manager.createUser(
        users
            .username("FoODy_NeT_AdMiN")
            .password(new BCryptPasswordEncoder().encode("FoODy_NeT_AdMiN"))
            .roles(USER, ADMIN)
            .build());

    manager.createUser(
        users
            .username("FoODy_NeT_SuPeR_AdMiN")
            .password(new BCryptPasswordEncoder().encode("FoODy_NeT_SuPeR_AdMiN"))
            .roles(USER, ADMIN, SUPER_ADMIN)
            .build());

    return manager;
  }
}
