package cn.eclubcc.config.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 添加认证逻辑供小程序使用
 * 直接判断给定密码和数据库相同
 *
 * @author Ikaros
 * @date 2020/3/31 17:49
 */
@Component
public class MiniProgramsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    @Qualifier(value = "UserDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 如果我们给定的密码和数据库查出来的密码不相等
        String password = authentication.getCredentials().toString();
        if (!StringUtils.equals(password, userDetails.getPassword())) {
            throw new BadCredentialsException(messages.getMessage("MiniProgramsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 用户传进来的用户名和密码
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
        if (userDetails == null) {
            throw new UsernameNotFoundException("MiniProgramsAuthenticationProvider.UsernameNotFoundException");
        }
        additionalAuthenticationChecks(userDetails, (UsernamePasswordAuthenticationToken) authentication);
        
        // return createSuccessAuthentication(username, authentication, userDetails);
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
    
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            UserDetails loadedUser = userDetailsService.loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
