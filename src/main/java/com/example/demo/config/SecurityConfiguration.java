package com.example.demo.config;

/**
 * Created by home on 6/5/2018.
 */

import com.example.demo.repository.UsersRepository;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Scanner;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UsersRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("**/secured/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll();
    }

    private PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return true;
            }
        };
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String inputUser = null;
        String inputPassword = null;
        System.out.println("\nPlease set the admin credentials for this web application");
        while (true) {
            System.out.print("user: ");
            inputUser = scanner.nextLine();
            System.out.print("password: ");
            inputPassword = scanner.nextLine();
            System.out.print("confirm password: ");
            String inputPasswordConfirm = scanner.nextLine();

            if (inputUser.isEmpty()) {
                System.out.println("Error: user must be set - please try again");
            } else if (inputPassword.isEmpty()) {
                System.out.println("Error: password must be set - please try again");
            } else if (!inputPassword.equals(inputPasswordConfirm)) {
                System.out.println("Error: password and password confirm do not match - please try again");
            } else {

                break;
            }
            System.out.println("");
        }
        scanner.close();

        if (inputUser != null && inputPassword != null) {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(getPasswordEncoder());
        }
    }
}
