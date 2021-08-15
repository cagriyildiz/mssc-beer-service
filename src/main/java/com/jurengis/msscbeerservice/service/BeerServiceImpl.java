package com.jurengis.msscbeerservice.service;

import com.jurengis.msscbeerservice.domain.Beer;
import com.jurengis.msscbeerservice.repository.BeerRepository;
import com.jurengis.msscbeerservice.web.controller.NotFoundException;
import com.jurengis.msscbeerservice.web.mappers.BeerMapper;
import com.jurengis.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Override
  public BeerDto getById(UUID beerId) {
    return beerMapper.beerToBeerDto(beerRepository.findById(beerId)
        .orElseThrow(NotFoundException::new));
  }

  @Override
  public BeerDto saveNewBeer(BeerDto beerDto) {
    Beer beer = beerMapper.beerDtoToBeer(beerDto);
    return beerMapper.beerToBeerDto(beerRepository.save(beer));
  }

  @Override
  public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
    Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
    beer.setBeerName(beerDto.getBeerName());
    beer.setBeerStyle(beerDto.getBeerStyle().name());
    beer.setPrice(beerDto.getPrice());
    beer.setUpc(beerDto.getUpc());
    return beerMapper.beerToBeerDto(beerRepository.save(beer));
  }
}
