package com.example.ArmInvest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SpringBootApplication
public class ArmInvestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArmInvestApplication.class, args);
	}

    @Bean
    public CommandLineRunner initData(InvestorRepository repoInvestor) {
        return (args) -> {

            Investor investor1 = new Investor("JackBauer", "24");
            repoInvestor.save(investor1);

        };
	}
        @Configuration
        class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        InvestorRepository repoInvestor;
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(email -> {
                Investor investor = repoInvestor.findByUserEmail(email);
                if (investor != null) {
                    return User
                            .withDefaultPasswordEncoder()
                            .username(investor.getEmail())
                            .password(investor.getPassword())
                            .roles("USER")
                            .build();
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + email);
                }
            });
        }
    }

    @Configuration
    @EnableWebSecurity
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers("/index.html/**",
                            "/web/games.html").permitAll();

            http.formLogin()
                    .loginPage("/api/login")
                    .usernameParameter("userEmail")
                    .passwordParameter("password")
                    .permitAll();

            http.logout().logoutUrl("/api/logout");

            http.csrf().disable();
            http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
            http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
            http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
            http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        }

        private void clearAuthenticationAttributes(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }


        }
    }

}

