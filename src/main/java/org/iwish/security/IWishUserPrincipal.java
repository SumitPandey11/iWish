package org.iwish.security;

import org.iwish.models.User;
import org.iwish.models.UserGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class IWishUserPrincipal implements UserDetails {
    private User user;
    private List<UserGroup> userGroups;

    public IWishUserPrincipal(User user, List<UserGroup> userGroups){
        super();
        this.user = user;
        this.userGroups = userGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(userGroups == null){
            return  Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorits = new HashSet<>();
        userGroups.forEach( group -> {
            grantedAuthorits.add(new SimpleGrantedAuthority(group.getAuthgroup()));
        });
        return grantedAuthorits;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
