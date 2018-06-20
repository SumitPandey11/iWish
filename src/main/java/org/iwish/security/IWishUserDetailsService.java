package org.iwish.security;

import org.iwish.models.User;
import org.iwish.models.data.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class IWishUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public IWishUserDetailsService(UserDao userDao){
        super();
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByName(username);
        if(user == null){
            throw  new UsernameNotFoundException("Cannot find username : " + username);
        }
        return new IWishUserPrincipal(user);
    }
}
