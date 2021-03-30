package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.Size;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.SizeService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SizeController.class)
@Import(SecurityConfig.class)
public class SizeControllerTest {

    @MockBean
    private SizeService sizeService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;

    private  List<Size> sizes;
    private  Size size1;
    private  Size size2;
    private  Pizza pizza;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        sizes = new ArrayList<Size>();
        pizza = new Pizza(1, true, "/scdcsd", "papa", "description", null, 10.00, "1,24,6", 4, null, null, null);
        size1 = new Size(1, "pepperoniSmall", 20, 320, 16, new byte[]{12, 32, 45, -11, 0, 43}, "/pepperoni20", pizza);
        size2 = new Size(2, "pepperoniLarge", 30, 700, 33, new byte[]{18, 22, 121, 0, 19, -67}, "/pepperoni30", pizza);
        sizes.add(size1);
        sizes.add(size2);
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenValidSizePizzaAndImageWhenInsertingSizePizzaReturnAllPizzaSizes() throws Exception{
        Size size3 = new Size(3, "pepperoniMedium", 25, 540, 22, new byte[]{32, -32, 45, -11, 100, 73}, "/pepperoni30", pizza);
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "WX20180207-134704@2x.png",
                "image/png",
                "pepperoniMedium.jpg".getBytes());
        BDDMockito.when(sizeService.createSize(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Size.class), ArgumentMatchers.any(MultipartFile.class))).thenReturn(sizes);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/size/3" )
                .file(file)
                .flashAttr("size", size3))
                 .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void givenPizzaIdWhenGettingAllSizesByPizzaIdReturnAllPizzaSizes() throws Exception {
        int id = 1;
        List<Size> sizeFind = new ArrayList<Size>();
        for (Size size: sizes
             ) {
            if (size.getPizza().getId() == id) sizeFind.add(size);
        }
        BDDMockito.given(sizeService.getAllSizesByPizzaId(id)).willReturn(sizeFind);
            mockMvc.perform(MockMvcRequestBuilders.get("/size/"+ id))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(size1, size2))))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }
    @Test
    public void givenPizzaIdAndPizzaNameWhenGettingPizzaSizeByPizzaIdAndSizeNameReturnAllSizesBySizeName() throws Exception {
        int id = 1;
        String name = "pepperoniSmall";
        Size sizeFind = null;
        for (Size size:sizes
             ) {
            if (size.getPizza().getId() == id && size.getName().equals(name)) sizeFind = size;
        }
        assert sizeFind != null;
        BDDMockito.given(sizeService.getSizeByPizzaId(id, name)).willReturn(sizeFind);

        mockMvc.perform(MockMvcRequestBuilders.get("/size/"+id+"/"+name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));
    }
    @Test
    public void givenPathSizeImageWhenGettingImageByPathReturnSuccessfulResponse() throws Exception {
        String path = "/pepperoni30";
        Size sizeFind = null;
        for (Size size: sizes
             ) {
            if (size.getPath().equals(path)) sizeFind = size;
        }
            assert sizeFind != null;
            BDDMockito.given(sizeService.getSizeImageByPath(path)).willReturn(sizeFind.getData());

            mockMvc.perform(MockMvcRequestBuilders.get("/size/image/"+ path))
                    .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void givenSizePathAndSizeNameWhenGettingImageByPathSizeAndNameSizeReturnSuccessfulResponse() throws Exception{
        String path = "/pepperoni30";
        String name = "pepperoniLarge";
        Size sizeFind = null;
        for (Size size: sizes
             ) {
            if (size.getPath().equals(path) && size.getName().equals(name)) sizeFind = size;
        }
        assert sizeFind != null;
        BDDMockito.given(sizeService.getSizeImageByNameType(path, name)).willReturn(sizeFind.getData());

        mockMvc.perform(MockMvcRequestBuilders.get("/size/image/" + path + "/" + name))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenSizeIdWhenDeletingSizeReturnSuccessfulResponseAndAllSizes() throws Exception{
        int id = 1;
        Size sizeFind = null;
        for (Size size: sizes
             ) {
            if (id == size.getId()) sizeFind = size;
        }
        assert sizeFind != null;
        sizes.remove(sizeFind);
        BDDMockito.given(sizeService.deleteSize(id)).willReturn(sizes);

        mockMvc.perform(MockMvcRequestBuilders.delete("/size/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(size2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2));
    }
}
