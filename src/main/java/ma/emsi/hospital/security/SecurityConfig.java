package ma.emsi.hospital.security;

import ma.emsi.hospital.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;
    //@Autowired : TO Be Remplaced with constructors
    private UserDetailsServiceImpl UserDetailsService;
    //@Autowired
    // private PasswordEncoder passwordEncoder;

    public SecurityConfig(DataSource dataSource, UserDetailsServiceImpl userDetailsService) {
        this.dataSource = dataSource;
        UserDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      /*  PasswordEncoder pE = passwordEncoder();
      auth.inMemoryAuthentication().withUser("admin").password(pE.encode("admin")).roles("ADMIN","USER");
        auth.inMemoryAuthentication().withUser("youssef").password(pE.encode("123456")).roles("USER");

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal,role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(pE);*/
        auth.userDetailsService(UserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeRequests().antMatchers("/").permitAll();
        //An authority can have many roles
        //http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        //http.authorizeRequests().antMatchers("/user/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority(  "USER");
        http.authorizeRequests().antMatchers("/admin/webjars/**").permitAll();
        http.authorizeRequests().antMatchers("/user/webjars/**").permitAll();
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");

    }

}
