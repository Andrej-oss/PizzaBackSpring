package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Avatar;
import com.pizza_shop.project.entity.Cart;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.AvatarService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AvatarController.class)
@Import({SecurityConfig.class})
public class AvatarControllerTest {

    @MockBean
    private AvatarService avatarService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;

    private  List<Avatar> avatars;
    private  Avatar avatar1;
    private  Avatar avatar2;
    private  User user1;
    private  User user2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public  void init(){
        avatars = new ArrayList<>();
        user1 =  new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        user2 =  new User(2, "Joe", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        avatar1 = new Avatar(1, "/bob", new byte[]{32,1,34,5,0,-11}, user1);
        avatar2 = new Avatar(2, "/joe", new byte[]{73,14,3,-50,100,-61,56}, user2);
        avatars.add(avatar1);
        avatars.add(avatar2);
    }
    @Test
    public void givenNothingWhenGettingAllAvatarsReturnAllAvatarsAndSuccessfulResponse() throws Exception{

        BDDMockito.when(avatarService.getAllAvatars()).thenReturn(avatars);

        mockMvc.perform(MockMvcRequestBuilders.get("api/avatar"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(avatar1, avatar2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
    @Test
    public void givenUserIdWhenGettingAvatarReturnAvatarImage() throws Exception{
        int id = 1;
        Avatar avatarFind = null;
        for (Avatar avatar: avatars
             ) {
            if (avatar.getUser().getId() == id) avatarFind = avatar;
        }
        assert avatarFind != null;
       BDDMockito.when(avatarService.getAvatarByUserId(id)).thenReturn(avatarFind);

       mockMvc.perform(MockMvcRequestBuilders.get("api/avatar/{id}", id))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser
    public void givenPathAvatarWhenGettingImageAvatarReturnImage() throws Exception{
        String path = "/bob";
        Avatar avatarFind = null;
        for (Avatar avatar: avatars
             ) {
            if (avatar.getPath().equals(path)) avatarFind = avatar;
        }
        assert avatarFind != null;
        BDDMockito.when(avatarService.getAvatarByPath(path)).thenReturn(avatarFind.getData());

        mockMvc.perform(MockMvcRequestBuilders.get("api/avatar/image{path}", path))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenAvatarIdWhenDeletingAvatarReturnSuccessfulResponse() throws Exception{
        int id = 3;
        Avatar avatar3 = new Avatar(3, "/joe", new byte[]{73,14,3,-50,100,-61,56}, user2);
        avatars.add(avatar3);
        Avatar avatarFind = null;
        for (Avatar avatar: avatars
             ) {
            if (avatar.getId() == id) avatarFind = avatar;
        }
        assert avatarFind != null;
        avatars.remove(avatarFind);
        BDDMockito.when(avatarService.deleteAvatar(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("api/avatar/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }
    @Test
    public void givenValidAvatarBodyWhenInsertingAvatarReturnAvatarAndSuccessfulResponse() throws Exception{
        final Avatar avatar = new Avatar(1, "/ford", new byte[]{2, 13, 5, 7, 4, 0}, user1);
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "WX20180207-134704@2x.png",
                "image/png",
                "beckon.jpg".getBytes());
        avatars.add(avatar);
        Mockito.when(avatarService.saveAvatar(ArgumentMatchers.anyInt(), any(Avatar.class), any(MultipartFile.class))).thenReturn(avatar);

        mockMvc.perform(MockMvcRequestBuilders.multipart("api/avatar/{id}", 1).file(file)
                .flashAttr("avatar", avatar))
                .andExpect(MockMvcResultMatchers.status().isCreated());
                 }
}
