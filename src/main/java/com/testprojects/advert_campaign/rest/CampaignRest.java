package com.testprojects.advert_campaign.rest;

import com.testprojects.advert_campaign.entity.Campaign;
import com.testprojects.advert_campaign.entity.User;
import com.testprojects.advert_campaign.repository.CampaignRepository;
import com.testprojects.advert_campaign.repository.UserRepository;
import com.testprojects.advert_campaign.vo.CampaignVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ad")
public class CampaignRest {


    private CampaignRepository campaignRepository;

    private UserRepository userRepository;

    @Autowired
    public CampaignRest(CampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    List<CampaignVo> getAllCampaigns() {
         return asCampaignVos(campaignRepository.findAll());
    }

    @RequestMapping(value = "/all/{partnerId}",method = RequestMethod.GET)
    List<CampaignVo> getCampaigns(@PathVariable String partnerId) {
        User user = userRepository.findByUserId(partnerId);
        return asCampaignVos(campaignRepository.findCampaignsByUser(user));
    }

    @RequestMapping(value = "/current",method = RequestMethod.GET)
    Collection<CampaignVo> getCurrentCampaigns() {
        return asCampaignVos(campaignRepository.findCurrentCampaigns());
    }

    @RequestMapping(value = "/current/{partnerId}",method = RequestMethod.GET)
    Collection<CampaignVo> getCurrentCampaignsForUser(@PathVariable String partnerId) {
        User user = userRepository.findByUserId(partnerId);
        return asCampaignVos(campaignRepository.findCurrentCampaignsForUser(user));
    }

    @RequestMapping(value = "/{partnerId}",method = RequestMethod.GET)
    ResponseEntity<?> getActiveCampaign(@PathVariable String partnerId) {
        ResponseEntity<?> responseEntity;
        User user = userRepository.findByUserId(partnerId);
        if(user == null){
            responseEntity = getUserDoesNotExistResponseEntity(partnerId);
        }else
        {
            Collection<Campaign> activeCampaigns = campaignRepository.findCurrentCampaignsForUser(user);
            if(activeCampaigns.size()>0) {
                responseEntity = ResponseEntity.ok(new CampaignVo(activeCampaigns.iterator().next()));
            }else{
                //responseEntity = ResponseEntity.ok(String.format("No active campaign exists for user %s",user.getUserId()));
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No active campaign exists for user %s",user.getUserId()));
            }
        }

        return responseEntity;
    }



    @RequestMapping(method = RequestMethod.POST,consumes="application/json")
    ResponseEntity<?> addCampaignData(@RequestBody CampaignVo campaignVo) {

        ResponseEntity responseEntity;
        User user = userRepository.findByUserId(campaignVo.getPartnerId());
        if(user!=null){
            Campaign campaign = new Campaign(user,campaignVo.getDuration(),campaignVo.getAdContent());
            Campaign result  = campaignRepository.save(campaign);
            responseEntity = ResponseEntity.accepted().body(String.format("Ad for %s created",campaignVo.getPartnerId()));

        }else
        {
            responseEntity = getUserDoesNotExistResponseEntity(campaignVo.getPartnerId());
        }

        return responseEntity;

    }

    private ResponseEntity getUserDoesNotExistResponseEntity(String user) {
        String s =String.format("User %s does not exist. Create user %s first.",user,user);
        ResponseEntity<String> responseEntity;
        responseEntity = (ResponseEntity.status(HttpStatus.FORBIDDEN).body(s));
        return responseEntity;
    }

    private List<CampaignVo> asCampaignVos(Collection<Campaign> campaigns){
          return campaigns.stream().map(CampaignVo::new).collect(Collectors.toList());
    }
}