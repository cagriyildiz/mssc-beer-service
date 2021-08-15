package com.jurengis.msscbeerservice.web.mappers;

import com.jurengis.msscbeerservice.domain.Beer;
import com.jurengis.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {

  BeerDto beerToBeerDto(Beer beer);

  Beer beerDtoToBeer(BeerDto beerDto);
}
