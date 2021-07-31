package web.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import web.config.handler.LoginSuccessHandler;
import web.service.UserService;
import web.service.UserServiceImpl;


@Configuration
@ComponentScan("web")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsService service;
    private LoginSuccessHandler handler;

    public SecurityConfig() {
    }

    @Autowired
    public SecurityConfig(UserDetailsService service, LoginSuccessHandler handler){
        this.service = service;
        this.handler = handler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Нам нужно добавить CharacterEncodingFilter перед фильтрами, которые читают свойства запроса в первый раз.
        // Есть securityFilterChain (стоит вторым. после metrica filter) , и мы можем добавить наш фильтр внутри него.
        // Первый фильтр (внутри цепочки безопасности), который читает свойства,
        // - это CsrfFilter, поэтому мы помещаем перед ним CharacterEncodingFilter.
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                //указываем логику обработки при логине
                .successHandler(handler)
                // указываем action с формы логина
                .loginProcessingUrl("/loginAction")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("username")
                .passwordParameter("password")
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                //выключаем кросс-доменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable(); //- попробуйте выяснить сами, что это даёт

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()

                .antMatchers("/").permitAll() // доступность всем

                //страница аутентификации доступна всем
                .antMatchers("/login").anonymous()

                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

    }

    // Необходимо для шифрования паролей
    // В данном примере не используется, отключен
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
