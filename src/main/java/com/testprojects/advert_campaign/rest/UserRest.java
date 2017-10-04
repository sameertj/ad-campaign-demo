package com.testprojects.advert_campaign.rest;

import com.testprojects.advert_campaign.entity.User;
import com.testprojects.advert_campaign.repository.UserRepository;
import com.testprojects.advert_campaign.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserRest {

    private UserRepository userRepository;

    @Autowired
    public UserRest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
        User getUser(@PathVariable String userId) {
        //this.validateUser(userId);
//        Optional<User> users = Optional.ofNullable(userRepository.findByUserId(userId));
//        if(users.isPresent()){
//            users.map((e->{
//                System.out.println("in user rest......");
//                System.out.println(e.getUserId());
//                return e;
//            }
//            ));
//
//        }
        return userRepository.findByUserId(userId);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    List<User> getAllUsers() {
        //this.validateUser(userId);
        return userRepository.findAll();
    }


    @RequestMapping(value = "/addUser",method = RequestMethod.POST,consumes="application/json")
    ResponseEntity<?> addUser(@RequestBody UserVo userVo) {

        ResponseEntity responseEntity;
        User user = userRepository.findByUserId(userVo.getUserId());
        if (user == null) {
            User newUser = new User(userVo);
            User result = userRepository.save(newUser);
            responseEntity = ResponseEntity.accepted().body(String.format("User %s %s %s Created",
                    userVo.getFirstName(),userVo.getMiddleName(),userVo.getLastName()));

        } else {
            responseEntity = (ResponseEntity.unprocessableEntity().body(String.format("User %s already exists",userVo.getUserId())));
        }

        return responseEntity;

    }

    @RequestMapping(value = "/addUserString",method = RequestMethod.POST)
    ResponseEntity<?> addUserString(@RequestBody  String userVo) {
        return ResponseEntity.ok(userVo);
    }


}
