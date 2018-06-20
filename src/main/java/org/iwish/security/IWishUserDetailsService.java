package org.iwish.security;

import org.iwish.models.User;
import org.iwish.models.UserGroup;
import org.iwish.models.data.UserDao;
import org.iwish.models.data.UserGroupDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IWishUserDetailsService implements UserDetailsService {

    private final UserDao userDao;
    private final UserGroupDao userGroupDao;

    public IWishUserDetailsService(UserDao userDao, UserGroupDao userGroupDao){
        super();
        this.userDao = userDao;
        this.userGroupDao = userGroupDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByName(username);

        if(user == null){
            throw  new UsernameNotFoundException("Cannot find username : " + username);
        }
        List<UserGroup> userGroups = userGroupDao.findByUsername(username);

        return new IWishUserPrincipal(user, userGroups);
    }
}
