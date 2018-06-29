package org.iwish.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private IWishUserDetailsService iWishUserDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(iWishUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(11));
        provider.setAuthoritiesMapper(rolesMapper());
        return provider;
    }

    public GrantedAuthoritiesMapper rolesMapper(){
        SimpleAuthorityMapper  authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        authorityMapper.setDefaultAuthority("USER");
        return authorityMapper;

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     *
     * @param http
     * @throws Exception
     * Permit all user to access "/","/user","/user/add","/css/*","/js/*", no authentication required for this endpoints
     * Authenticate Any other request
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/user","/user/add","/css/*", "/static/js/*").permitAll()
                .anyRequest().permitAll()//authenticated()
                .and()
                .httpBasic();
    }

    /**
     * Overriding the base method and making it poublic.
     * The UserDetailsService interface is used to retrieve user-related data.
     * It has one method named loadUserByUsername() which finds a user entity based on the username and
     * can be overridden to customize the process of finding the user.
     * @return
     */
/*    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();
        users.add(User.withDefaultPasswordEncoder().username("sumit").password("password").roles("USER","ADMIN").build());
        users.add(User.withDefaultPasswordEncoder().username("dev").password("password").roles("USER").build());
        return new InMemoryUserDetailsManager(users);
    }*/
}
