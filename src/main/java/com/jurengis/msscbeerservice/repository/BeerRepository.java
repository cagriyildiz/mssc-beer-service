package com.jurengis.msscbeerservice.repository;

import com.jurengis.msscbeerservice.domain.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

  Page<Beer> findAllByBeerName(String beerName, PageRequest pageRequest);

  Page<Beer> findAllByBeerStyle(String beerStyle, PageRequest pageRequest);

  Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, String beerStyle, PageRequest pageRequest);
}
