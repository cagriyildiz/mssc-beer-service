package com.jurengis.msscbeerservice.service;

import com.jurengis.msscbeerservice.web.model.BeerDto;
import com.jurengis.msscbeerservice.web.model.BeerPagedList;
import com.jurengis.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
  BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand);

  BeerDto getById(UUID beerId, boolean showInventoryOnHand);

  BeerDto getByUpc(String upc, boolean showInventoryOnHand);

  BeerDto saveNewBeer(BeerDto beerDto);

  BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
