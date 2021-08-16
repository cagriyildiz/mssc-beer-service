package com.jurengis.msscbeerservice.service;

import com.jurengis.msscbeerservice.domain.Beer;
import com.jurengis.msscbeerservice.repository.BeerRepository;
import com.jurengis.msscbeerservice.web.controller.NotFoundException;
import com.jurengis.msscbeerservice.web.mappers.BeerMapper;
import com.jurengis.msscbeerservice.web.model.BeerDto;
import com.jurengis.msscbeerservice.web.model.BeerPagedList;
import com.jurengis.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Override
  public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {
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

    return new BeerPagedList(beerPage
        .getContent()
        .stream()
        .map(beerMapper::beerToBeerDto)
        .collect(Collectors.toList()),
        PageRequest
            .of(beerPage.getPageable().getPageNumber(),
                beerPage.getPageable().getPageSize()),
        beerPage.getTotalElements()
    );
  }

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
