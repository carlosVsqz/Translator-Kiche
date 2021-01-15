package com.nosysask.esc.translator.service.serviceimpl;

import com.nosysask.esc.translator.model.UserDto;
import com.nosysask.esc.translator.repository.UserRepository;
import com.nosysask.esc.translator.service.UserService;
import com.nosysask.esc.translator.utils.Utils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Utils utils;

    /**
     * Initialize Logger object
     */
    org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserDto createUser(UserDto user) {
        UserDto returned = new UserDto();

        logger.info("UserService -> createUser(user) method is called. Checking database for existing user details");
        if (userRepository.findByEmail(user.getEmail()) != null) {
            logger.error("Error! Record already exists.");
            throw new RuntimeException("Record already exists.");
        }

        logger.info("Creating new user");

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setPicURL("temporal_url_picture_user");
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        String publicUserId = utils.generateUserId(30);

        userDto.setUserId(publicUserId);
        userDto.setUserType("user");

        logger.info("Saving the user to the database -> users");

        UserDto storeDetails = userRepository.save(userDto);
        BeanUtils.copyProperties(storeDetails, returned);

        logger.info("Returning the user details to the UserController via UserDto object");
        return returned;
    }

    @Override
    public UserDto getUser(String email) {

        logger.info("UserService -> getUser(email) method is called. Finding the user");

        UserDto userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {

            logger.error("Can't find the user id from the database");

            throw new UsernameNotFoundException(email);
        }

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        logger.info("User details found. Returning the user to the UserController via UserDto");

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        logger.info("UserService -> getUser(userId) method is called. Finding the user");

        UserDto returnValue = new UserDto();
        UserDto userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {

            logger.error("Can't find the user id from the database");

            throw new UsernameNotFoundException(userId);
        }
        BeanUtils.copyProperties(userEntity, returnValue);

        logger.info("User details found. Returning the user to the UserController via UserDto object");

        return returnValue;


    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {

        logger.info("UserService -> updateUser(userId, user) method is called. Finding the user");

        UserDto returnValue = new UserDto();

        UserDto userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {

            logger.error("Can't find the user id from the database");

            throw new UsernameNotFoundException(userId);
        }

        logger.info("User details found. Updating user details");

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        logger.info("Saving updated details to the database -> users");

        UserDto updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, returnValue);

        logger.info("Returning the user to the UserController via UserDto object");

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("UserService -> deleteUser(userId) method is called. Finding the user");

        UserDto userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {

            logger.error("Can't find the user id from the database");

            throw new UsernameNotFoundException(userId);
        }

        logger.info("User details found. Deleting the user from the database -> users");

        userRepository.delete(userEntity);

    }

    @Override
    public List<UserDto> getUsers() {
        logger.info("UserService -> getUsers() method is called. Collection all users details");

        List<UserDto> returnValue = new ArrayList<>();

        Iterable<UserDto> iterableObjects = userRepository.findAll();

        for (UserDto userEntity : iterableObjects) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        logger.info("Users' details found. Returning users to the UserController via List<UserDto>");

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        logger.info("UserService -> loadUserByUsername(email) method is called. Finding the user");

        UserDto userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {

            logger.error("Can't find the user id from the database");

            throw new UsernameNotFoundException(email);
        }

        logger.info("User details found. Returning the user via new User object");

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
