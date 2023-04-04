package com.example.let.domain;

import com.example.let.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@AllArgsConstructor
@Builder
@Data
public class UserForSecurity implements UserDetails {

    private final PasswordEncoder passwordEncoder;

    private static final long serialVersionUID = 1L;

    @NonNull
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_GUEST");
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(authority);

        if (user.getUserType() == 'S') {  // 학생
            authority = new SimpleGrantedAuthority("ROLE_USER");
            authorities.add(authority);
        }
        else if (user.getUserType() == 'T') {  // 교사
            authority = new SimpleGrantedAuthority("ROLE_USER");
            authorities.add(authority);
            authority = new SimpleGrantedAuthority("ROLE_TEACHER");
            authorities.add(authority);
        }
        else if (user.getUserType() == 'D') {  // 디바이스
            authority = new SimpleGrantedAuthority("ROLE_DEVICE");
            authorities.add(authority);
        }

        log.info(authorities.toString());
        return authorities;
    }

    @Override
    public String getPassword() {
        return ((user != null) ? user.getPassword() : null);
    }

    @Override
    public String getUsername() {
        return ((user != null) ? user.getId() : null);
    }

    @Override
    public boolean isAccountNonExpired() {
        if (user != null) {
            if('N' == user.getWithdrawedYn()) {
                return true;
            } else {
                throw new GlobalException(HttpStatus.BAD_REQUEST, "withdrawn account");
            }
        }

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (user != null) {
            if('Y' == user.getApprovedYn()) {
                return true;
            } else {
                throw new GlobalException(HttpStatus.BAD_REQUEST, "unauthorized account");
            }
        }

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

