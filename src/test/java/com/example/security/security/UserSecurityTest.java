package com.example.security.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "aaa", password = "bbb", roles = "USER")
    public void testSecurityEndPoint() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/home"));
        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "peter", password = "peter@123", roles = "USER")
    public void test404ForNonExistentEndpoint() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/nonexistent"));
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testRememberMe() throws Exception {
        mockMvc.perform(formLogin()
                        .user("user")
                        .password("password")
                )
                .andExpect(authenticated().withUsername("user").withRoles("USER"));
    }

}
