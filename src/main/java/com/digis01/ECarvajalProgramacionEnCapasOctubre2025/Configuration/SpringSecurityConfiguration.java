package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Configuration;



import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Component.JwtAuthenticationFilter;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Service.UserDetailsJPAService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    
    private final UserDetailsJPAService userDetailsJPAService;
    
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SpringSecurityConfiguration (UserDetailsJPAService userDetailsJPAService1, 
            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
            JwtAuthenticationFilter jwtAuthFilter){
        
        this.userDetailsJPAService = userDetailsJPAService1;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.jwtAuthFilter = jwtAuthFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        
        http.authorizeHttpRequests( authorizeRequests -> authorizeRequests
                .requestMatchers("/usuario/detailUserName/{userName}").hasRole("Alumno")
                .requestMatchers("/usuario/**").hasAnyRole("Administrador", "Maestro")
                .requestMatchers("/login").permitAll() // Rutas públicas
                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                
                .formLogin( form -> form 
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/login/success")
                        .successHandler(customAuthenticationSuccessHandler)
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
    
        return new BCryptPasswordEncoder();
        
//      return NoOpPasswordEncoder.getInstance();

        
    }
    
}
