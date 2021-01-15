package com.nosysask.esc.translator.controller;

import com.nosysask.esc.translator.model.UserDto;
import com.nosysask.esc.translator.service.UserService;
import com.nosysask.esc.translator.service.serviceimpl.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Initialize Logger object
     */
    org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @CrossOrigin
    @ApiOperation(value = "The Get User Details Web Service Endpoint", notes = "This web service endpoint returns the User detials with json format or xml format")
    @ApiImplicitParams({ @ApiImplicitParam(name = "authorization", value = "Bearer JWT Token", paramType = "header")})
    @GetMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserDto getUser(@PathVariable String id){
        UserDto userDtoReturned = new UserDto();

        logger.info("UserController -> getUser(id) method has been called");

        UserDto auxUserDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(auxUserDto, userDtoReturned);

        logger.info("Return the user to the requester");

        return userDtoReturned;
    }

    @CrossOrigin
    @ApiOperation(value = "The User Registration Web Service Endpoint", notes = "This web service endpoint returns the User detials with json format or xml format when the user is created")
    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public UserDto createUser(@RequestBody UserDto userDto){
        UserDto newUserReturned = new UserDto();
        logger.info("UserController -> createUser(userDetails method has been called)");
        BeanUtils.copyProperties(userDto, newUserReturned);
        logger.info("Return the user to the requester");
        return userService.createUser(newUserReturned);
    }

}
