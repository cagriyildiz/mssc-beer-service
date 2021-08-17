package com.jurengis.msscbeerservice.web.controller;

import com.jurengis.msscbeerservice.service.BeerService;
import com.jurengis.msscbeerservice.web.model.BeerDto;
import com.jurengis.msscbeerservice.web.model.BeerPagedList;
import com.jurengis.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

  private static final String DEFAULT_PAGE_NUMBER = "0";
  private static final String DEFAULT_PAGE_SIZE = "25";

  private final BeerService beerService;

  @GetMapping(produces = {"application/json"})
  public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                                 @RequestParam(value = "beerName", required = false) String beerName,
                                                 @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                 @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand) {
    BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
    return new ResponseEntity<>(beerList, HttpStatus.OK);
  }

  @GetMapping("/{beerId}")
  public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId,
                                             @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand) {
    BeerDto beerDto = beerService.getById(beerId, showInventoryOnHand);
    return new ResponseEntity<>(beerDto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<BeerDto> saveNewBeer(@RequestBody @Validated BeerDto beerDto) {
    BeerDto newBeer = beerService.saveNewBeer(beerDto);
    return new ResponseEntity<>(newBeer, HttpStatus.CREATED);
  }

  @PutMapping("/{beerId}")
  public ResponseEntity<BeerDto> updateBeer(@PathVariable UUID beerId, @RequestBody @Validated BeerDto beerDto) {
    BeerDto updatedBeer = beerService.updateBeer(beerId, beerDto);
    return new ResponseEntity<>(updatedBeer, HttpStatus.NO_CONTENT);
  }
}
