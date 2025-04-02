package br.weg.net.security.sec.config;

import br.weg.net.security.sec.service.UsuarioAutenticacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    SecurityContextRepository securityContextRepository;

//    @Bean
//    public UserDetailsService users(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        manager.createUser(
//                User.builder().username("admin").password("Senh@Fort3").build());
//
//        manager.createUser(
//                User.builder().username("user").password("Fort3Senh@").build());
//
//        return manager;
//    }


    @Bean
    public SecurityContextRepository securityContextRepository() {
        if (securityContextRepository == null) {
            securityContextRepository = new HttpSessionSecurityContextRepository();
        }
        return securityContextRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityContext(
                config ->
                        config.securityContextRepository(securityContextRepository()));

        http.csrf(AbstractHttpConfigurer::disable);

        http.formLogin(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth ->{
            auth.requestMatchers(
                    HttpMethod.POST,"/api/auth/login", "/api/auth/logout" )
                    .permitAll()
                    .anyRequest().authenticated();
        });
//        http.authorizeHttpRequests(auth ->{
//            auth.requestMatchers(
//                            HttpMethod.GET,"/api/auth/user")
//                    .permitAll();
//        });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationProvider(UsuarioAutenticacaoService uds) {
        DaoAuthenticationProvider ap = new DaoAuthenticationProvider();
        ap.setUserDetailsService(uds);
        ap.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(ap);
    }

    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }
}