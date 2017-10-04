package com.testprojects.advert_campaign.entity;

import com.testprojects.advert_campaign.vo.UserVo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @OneToMany(mappedBy = "user")
    private Set<Campaign> campaigns = new HashSet<>();

    @Id
    @GeneratedValue
    private long id;

    private String userId;

    private String firstName;

    private String middleName;

    private String lastName;

    public User() {
    }

    public User(String userId, String firstName, String middleName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public User(UserVo userVo){
       this(userVo.getUserId(),userVo.getFirstName(),userVo.getMiddleName(),userVo.getLastName());
    }

    private boolean multipleCampaigns = false;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMultipleCampaigns() {
        return multipleCampaigns;
    }

    public void setMultipleCampaigns(boolean multipleCampaigns) {
        this.multipleCampaigns = multipleCampaigns;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
