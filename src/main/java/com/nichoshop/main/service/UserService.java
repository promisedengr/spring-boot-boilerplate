package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.repository.RoleRepository;
import com.nichoshop.main.repository.UserRepository;
import com.nichoshop.main.util.Constants;
import com.nichoshop.main.repository.EmailConfirmRepository;
import com.nichoshop.main.model.User;
import com.nichoshop.main.model.EmailConfirm;
import com.nichoshop.main.model.Role;

@Service
public class UserService {
   @Autowired
   UserRepository userRepository;
   @Autowired
   RoleRepository roleRepository;
   @Autowired
   EmailConfirmRepository emailConfirmRepository;

   public List<User> getAllUsers() {
      List<User> Users = new ArrayList<User>();
      userRepository.findAll().forEach(User -> Users.add(User));
      return Users;
   }

   public User getUserById(Long id) {
      return userRepository.findById(id).get();
   }

   public User getUserByUsernameOrEmail(String userString) {
      return userRepository.findByUsernameOrEmail(userString);
   }

   public void changePassword(String username, String password) {
      User user = userRepository.findByUsernameOrEmail(username);
      user.setPassword(password);
      userRepository.save(user);
   }

   public User getUserByEmail(String email) {
      return userRepository.findByEmail(email);
   }

   public void saveOrUpdate(User User) {
      userRepository.save(User);
   }

   public void saveEmailConfirm(EmailConfirm emailConfirm) {
      emailConfirmRepository.save(emailConfirm);
   }

   public void deleteUserById(Long id) {
      userRepository.deleteById(id);
   }

   public void addAddressToUser(Long userId, Long addressId) {
      User userFromDb = userRepository.findById(userId).get();
      userFromDb.setAddressId(addressId);
      userFromDb.setFromAddressId(addressId);
      userFromDb.setToAddressId(addressId);
      userFromDb.setReturnAddressId(addressId);
      userRepository.save(userFromDb);
   }

   public void updateUserPhoneNum(Long userId, String phoneNum) {
      User userFromDb = userRepository.findById(userId).get();
      userFromDb.setPhone(phoneNum);
      userRepository.save(userFromDb);
   }

   public void confirmPhone(Long userId) {
      userRepository.confirmPhone(userId);
   }

   public Boolean checkEmailAlreadyExists(String email) {
      boolean userExists = false;
      User existingUser = userRepository.findByEmail(email);
      if (existingUser != null) {
         userExists = true;
      }
      return userExists;
   }

   public Boolean confirmEmail(String code) {
      EmailConfirm emailConfirm = emailConfirmRepository.findByCode(code);
      if (emailConfirm != null) {
         Long duration = System.currentTimeMillis() - emailConfirm.getCreatedAt().getTime();

         if (duration < Constants.sucExpire.emailConfirm) {
            User user = userRepository.findById(emailConfirm.getUserId()).get();
            if (user != null) {
               user.setEmailConfirmed(true);
               userRepository.save(user);
               return true;
            } else
               return false;
         } else
            return false;
      } else {
         return false;
      }
   }

   public Boolean checkUsernameAlreadyExists(String username) {
      boolean userExists = false;
      User existingUser = userRepository.findByUsername(username);
      if (existingUser != null) {
         userExists = true;
      }
      return userExists;
   }

   public Boolean hasAdminRole(Long userId) {
      Role role = roleRepository.findByUserId(userId);
      if (role.getType() == "ROLE_ADMIN" || role.getType() == "ROLE_USER") {
         return true;
      } else
         return true;
   }

   public String getEmailConfirmCode(Long userId) {
      EmailConfirm emailConfirm = emailConfirmRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Confirmation code is not exist."));
      Date createdAt = new Date();
      emailConfirm.setCreatedAt(createdAt);
      emailConfirmRepository.save(emailConfirm);
      return emailConfirm.getCode();

   }

}