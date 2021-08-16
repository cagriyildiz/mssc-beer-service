package com.jurengis.msscbeerservice.web.mappers;

import com.jurengis.msscbeerservice.domain.Beer;
import com.jurengis.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

  @Mapping(target = "quantityOnHand", ignore = true)
  BeerDto beerToBeerDto(Beer beer);

  @Mapping(target = "minOnHand", ignore = true)
  @Mapping(target = "quantityToBrew", ignore = true)
  Beer beerDtoToBeer(BeerDto beerDto);
}
