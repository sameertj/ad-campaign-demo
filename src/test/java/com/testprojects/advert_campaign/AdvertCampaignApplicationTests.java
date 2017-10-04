package com.testprojects.advert_campaign;

import com.testprojects.advert_campaign.entity.Campaign;
import com.testprojects.advert_campaign.entity.User;
import com.testprojects.advert_campaign.repository.CampaignRepository;
import com.testprojects.advert_campaign.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertCampaignApplicationTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MediaType plainContent = new MediaType(MediaType.TEXT_PLAIN.getType(),MediaType.TEXT_PLAIN.getSubtype(),Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        HttpMessageConverter mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                mappingJackson2HttpMessageConverter);
    }

    String[] userIds = {"a", "b", "c"};
    String[] firstNames = {"first-of-a", "first-of-b", "first-of-c"};
    String[] middleNames = {"middle-of-a", "middle-of-b", "middle-of-c"};
    String[] lastNames = {"last-of-a", "last-of-b", "last-of-c"};

    private List<User> userList;

    private List<Campaign> campaignListOfA;
    private List<Campaign> campaignListOfB;
    private List<Campaign> campaignListOfC;


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.campaignRepository.deleteAllInBatch();
        this.userRepository.deleteAllInBatch();
    }

    private void createInitialData(int interval,int campaigNum) {
        userList = new ArrayList<>();

        for (int i = 0; i < userIds.length; i++) {
            User user = new User(userIds[i], firstNames[i], middleNames[i], lastNames[i]);
            User u = userRepository.save(user);
            userList.add(u);
            List<Campaign> campaigns = campaigns(u,interval,campaigNum);
            switch (u.getUserId()) {
                case "a":
                    campaignListOfA = campaigns;
                    break;
                case "b":
                    campaignListOfB = campaigns;
                    break;
                case "c":
                    campaignListOfC = campaigns;
                    break;
            }

        }
    }


    public List<Campaign> campaigns(User user,int interval,int campaignNum) {

        long durationInSeconds = interval + (campaignNum * interval);
        List<Campaign> savedCampaigns = new ArrayList<>();
        for (int i = 0; i < campaignNum; i++) {

            String adContent = String.format("adcontent %d  for user %s ", i, user.getUserId());
            long duration = durationInSeconds - interval;
            Campaign campaign = new Campaign(user, duration, adContent);
            Campaign savedCampaign = campaignRepository.save(campaign);
            savedCampaigns.add(savedCampaign);
        }

        return savedCampaigns;
    }

    @Test
    public void readUserA() throws Exception {
        createInitialData(10,10);
        mockMvc.perform(get("/user/" + userIds[0]))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.userId", is(userIds[0])));

    }

    @Test
    public void readUserAActiveCampaign() throws Exception {

        createInitialData(10,10);
        mockMvc.perform(get("/ad/" + userIds[0]))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.duration", is((int) campaignListOfA.get(campaignListOfA.size() - 1).getDuration()/1000))).
                andExpect(jsonPath("$.ad_content", is(campaignListOfA.get(campaignListOfA.size() - 1).getAdContent()))).
                andExpect(jsonPath("$.partner_id", is(userList.get(0).getUserId())));
    }

    @Test
    public void readUserANoActiveCampaign() throws Exception {

        createInitialData(1,2);
        Thread.sleep(3000);
        mockMvc.perform(get("/ad/" + userIds[0]))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(plainContent));
    }


}
