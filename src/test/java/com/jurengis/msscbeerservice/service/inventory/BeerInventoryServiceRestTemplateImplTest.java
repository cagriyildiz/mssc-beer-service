package com.jurengis.msscbeerservice.service.inventory;

import com.jurengis.msscbeerservice.bootstrap.BeerLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

  @Autowired
  BeerInventoryService beerInventoryService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void getOnHandInventory() {
    Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
    System.out.println(qoh);
  }
}