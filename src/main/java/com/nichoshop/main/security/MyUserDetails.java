package com.nichoshop.main.security;

import lombok.RequiredArgsConstructor;

import com.nichoshop.main.model.Role;
import com.nichoshop.main.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nichoshop.main.repository.RoleRepository;
import com.nichoshop.main.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User appUser = userRepository.findByUsername(username);
    List<RoleEum> role = new ArrayList<RoleEum>();

    if (appUser == null) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    Role roleEntity = roleRepository.findByUserId(appUser.getId());

    if (roleEntity.getType().equals(RoleEum.ROLE_ADMIN.getAuthority())) {
      role.add(RoleEum.ROLE_ADMIN);
    } else if (roleEntity.getType().equals(RoleEum.ROLE_USER.getAuthority())) {

      role.add(RoleEum.ROLE_USER);
    } else if (roleEntity.getType().equals(RoleEum.ROLE_CS.getAuthority())) {

      role.add(RoleEum.ROLE_CS);
    }

    return org.springframework.security.core.userdetails.User//
        .withUsername(username)//
        .password(appUser.getPassword())//
        .authorities(role)//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
