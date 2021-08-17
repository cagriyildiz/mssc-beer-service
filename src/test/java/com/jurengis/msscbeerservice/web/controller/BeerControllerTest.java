package com.jurengis.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jurengis.msscbeerservice.bootstrap.BeerLoader;
import com.jurengis.msscbeerservice.service.BeerService;
import com.jurengis.msscbeerservice.web.model.BeerDto;
import com.jurengis.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private BeerService beerService;

  @Test
  void getBeerById() throws Exception {
    given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDto());
    mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void saveNewBeer() throws Exception {
    BeerDto beerDto = getValidBeerDto();
    String beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

    mockMvc.perform(post("/api/v1/beer/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
        .andExpect(status().isCreated());
  }

  @Test
  void updateBeer() throws Exception {
    BeerDto beerDto = getValidBeerDto();
    String beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

    mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID())
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
        .andExpect(status().isNoContent());
  }

  BeerDto getValidBeerDto() {
    return BeerDto.builder()
        .beerName("My Beer")
        .beerStyle(BeerStyleEnum.ALE)
        .price(new BigDecimal("2.99"))
        .upc(BeerLoader.BEER_1_UPC)
        .build();
  }
}