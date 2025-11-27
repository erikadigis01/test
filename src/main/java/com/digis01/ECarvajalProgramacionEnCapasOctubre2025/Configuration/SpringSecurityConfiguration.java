package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Configuration;



import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Service.UserDetailsJPAService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    
    private final UserDetailsJPAService userDetailsJPAService;
    
    public SpringSecurityConfiguration (UserDetailsJPAService userDetailsJPAService1){
        
        this.userDetailsJPAService = userDetailsJPAService1;
        
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        
        http.authorizeHttpRequests( configurer -> configurer
                .requestMatchers("/loginUsuario").permitAll()
                .requestMatchers("/usuario/**")
                .hasAnyRole("Administrador", "Alumno", "Maestro")
                .anyRequest().authenticated())
                .formLogin( form -> form 
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/usuario", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .userDetailsService(userDetailsJPAService);
                
        
        return http.build();
        
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    
       // return new BCryptPasswordEncoder();
       return new BCryptPasswordEncoder();

        
    }
    
}
