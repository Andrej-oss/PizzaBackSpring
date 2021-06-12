package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.dto.PizzaDto;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.PizzaService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.plugins.MockMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(PizzaController.class)
@Import(SecurityConfig.class)
public class PizzaControllerTest {

    @MockBean
    private PizzaService pizzaService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private List<Pizza> pizzas;
    private Pizza pizza1;
    private Pizza pizza2;

    @BeforeEach
    public void init(){
        pizzas = new ArrayList<Pizza>();
        pizza1 = new Pizza(1, true, "/scdcsd", "papa", "description", null, 10.00, "1,24,6", 4, null, null, null);
        pizza2 = new Pizza(2, false, "/sfdsfd", "lala", "description", new byte[]{45, 7, 0, 7}, 123.00, "14,24,6", 4, null, null, null);
        pizzas.add(pizza1);
        pizzas.add(pizza2);
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenValidPizzaBodyWhenInsertingPizzaReturnAllPizzas() throws Exception{
        Pizza pizza3 = new Pizza(3, true, "/grgfd", "hawaii", "description", null, 10.00, "1,24,6", 4, null, null, null);
        pizzas.add(pizza3);
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "WX20180207-134704@2x.png",
                "image/png",
                "garlic.jpg".getBytes());
        BDDMockito.when(pizzaService.createPizza(ArgumentMatchers.any(Pizza.class), ArgumentMatchers.any(MultipartFile.class))).thenReturn(pizzas);

        mockMvc.perform(MockMvcRequestBuilders.multipart("api/pizza")
                .file(image)
                .flashAttr("pizza", pizza3))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(pizza1, pizza2, pizza3))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("hawaii"));
    }

    @Test
    @WithMockUser
    public void givenNothingWhenGettingAllPizzasReturnAllPizzas() throws Exception{
        BDDMockito.given(pizzaService.getAllPizzas()).willReturn(pizzas);

        mockMvc.perform(MockMvcRequestBuilders.get("api/pizza"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(pizza1, pizza2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("papa"));
    }
    @Test
    @WithMockUser
    public void givenPathImageWhenGettingImagePizzaReturnSuccessfulResponse() throws Exception{
        String path = "/sfdsfd";
         Pizza pizza = null;
        for (Pizza pizza3: pizzas) {
             if (pizza3.getPath().equals(path)) pizza = pizza3;
        };
        BDDMockito.given(pizzaService.getPizzaImage(pizza.getPath())).willReturn(pizza.getData());

        mockMvc.perform(MockMvcRequestBuilders.get("api/pizza/image/sfdsfd"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser
    public void givenPageRequestWhenGettingPaginationOfPizzasReturnPizzaDto() throws Exception{
        int page = 0;
        int size = 2;
        String sort = "name";
        String type = "desc";
        final PageRequest pageRequest = PageRequest.of(page, size, type.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending());
        BDDMockito.given(pizzaService.getSortedPizzas(pageRequest)).willReturn(new PizzaDto(pizzas, pizzas.size(), size, pizzas.size()/size, page));

        mockMvc.perform(MockMvcRequestBuilders.get("api/pizza/sort?page=" + page + "&size="+ size +"&type="+ type +"&sort=" + sort))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(pizzas.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(pizzas.size()/size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pizzas[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pizzas[0].name").value("papa"));
    }
    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void givenPizzaIdWhenDeletingPizzaReturnSuccessfulResponseAndAllPizzas() throws  Exception{
        int id = 1;
        BDDMockito.given(pizzaService.deletePizza(id)).willReturn(pizzas);
        Pizza pizzaFind = null;
        for ( Pizza pizza : pizzas
             ) {
            if (pizza.getId() == id) pizzaFind = pizza;
        }
        pizzas.remove(pizzaFind);
        mockMvc.perform(MockMvcRequestBuilders.delete("api/pizza/{id}", pizzaFind.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(pizza2))))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2));
    }

}
