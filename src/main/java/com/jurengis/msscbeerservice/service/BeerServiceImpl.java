package com.jurengis.msscbeerservice.service;

import com.jurengis.msscbeerservice.domain.Beer;
import com.jurengis.msscbeerservice.repository.BeerRepository;
import com.jurengis.msscbeerservice.web.controller.NotFoundException;
import com.jurengis.msscbeerservice.web.mappers.BeerMapper;
import com.jurengis.msscbeerservice.web.model.BeerDto;
import com.jurengis.msscbeerservice.web.model.BeerPagedList;
import com.jurengis.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
  @Override
  public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand) {
    Page<Beer> beerPage;

    if (!ObjectUtils.isEmpty(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
      beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle.name(), pageRequest);
    } else if (!ObjectUtils.isEmpty(beerName)) {
      beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
    } else if (!ObjectUtils.isEmpty(beerStyle)) {
      beerPage = beerRepository.findAllByBeerStyle(beerStyle.name(), pageRequest);
    } else {
      beerPage = beerRepository.findAll(pageRequest);
    }

    Function<Beer, BeerDto> mapBeerToBeerDto = showInventoryOnHand ?
        beerMapper::beerToBeerDtoWithInventory : beerMapper::beerToBeerDto;
    return new BeerPagedList(beerPage
        .getContent()
        .stream()
        .map(mapBeerToBeerDto)
        .collect(Collectors.toList()),
        PageRequest
            .of(beerPage.getPageable().getPageNumber(),
                beerPage.getPageable().getPageSize()),
        beerPage.getTotalElements()
    );
  }

  @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
  @Override
  public BeerDto getById(UUID beerId, boolean showInventoryOnHand) {
    Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
    return showInventoryOnHand ? beerMapper.beerToBeerDtoWithInventory(beer) : beerMapper.beerToBeerDto(beer);
  }

  @Cacheable(cacheNames = "beerUpcCache", key = "#upc", condition = "#showInventoryOnHand == false")
  @Override
  public BeerDto getByUpc(String upc, boolean showInventoryOnHand) {
    System.out.println("sa");
    Beer beer = beerRepository.findByUpc(upc);
    return showInventoryOnHand ? beerMapper.beerToBeerDtoWithInventory(beer) : beerMapper.beerToBeerDto(beer);
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
