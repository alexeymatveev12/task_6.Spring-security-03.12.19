package task5.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//

    @Configuration
    @EnableWebSecurity
    public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

        //@Autowired
        private UserDetailsService userDetailsService;



        @Autowired
        public void setUserDetailsService(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Autowired
        SpringSecurityConfig(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }
//    @Bean
//    public UserDetailsService getUserDetailsService(){
//        return new UserDetailsServiceImpl();
//    }


//    @Autowired
//    public void setUserDetailsService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

        ///&&&&&&&&&&&&&&&&&?????????????????????????????????????????????????????
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        //Перенаправление на разные страницы после входа в Spring Security
    @Bean
    public AuthenticationSuccessHandler userAuthenticationSuccessHandler() {
        return new UserAuthenticationSuccessHandler();
    }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);//.passwordEncoder(passwordEncoder());
            //auth.userDetailsService()
//        auth.inMemoryAuthentication().withUser("user").password("user").roles("ROLE_USER  ");
//        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ROLE_ADMIN");
//        auth.inMemoryAuthentication().withUser("superadmin").password("superadmin").roles("SUPERADMIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            System.out.println("ROLE_USER + ROLE_ADMIN");
            http
                    .authorizeRequests()
                    .antMatchers("/read").access("hasRole('USER') or hasRole('ADMIN')")
                    .antMatchers("/user/**").access("hasRole('USER')")
                    .antMatchers("/admin/**").access("hasRole('ADMIN')")

                    .anyRequest().anonymous() //для того чтобы /login не был доступен для авторизованных пользователей
                    .and()

                    .formLogin()
                    .loginPage("/login")
                    //куда перенаправлять после логина
                     .successHandler(userAuthenticationSuccessHandler()) //стратегия перенаправления после /login
                    .failureUrl("/login?error=true")
                    .and()

                    .logout()
                    .logoutUrl("/perform_logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .and()

                    //перенаправляем не на /403 а на домашнюю
                    //.exceptionHandling().accessDeniedPage("/home") //обработка ошибок (404)
                   // .and()

                    .csrf()
                    .disable()
            ;

        }
    }

