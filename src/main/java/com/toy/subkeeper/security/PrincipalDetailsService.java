package com.toy.subkeeper.security;

import com.toy.subkeeper.domain.User;
import com.toy.subkeeper.exception.NoMatchingDataException;
import com.toy.subkeeper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public PrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *  principalDetails 생성을 위한 함수.
     *  username으로 user 조회, principalDetails 생성
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new NoMatchingDataException("username : " + username);
        }

        return new PrincipalDetails(user);
    }
}
