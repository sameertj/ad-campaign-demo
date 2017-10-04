package com.testprojects.advert_campaign.repository;

import com.testprojects.advert_campaign.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    User findByUserId(String userId);
}



