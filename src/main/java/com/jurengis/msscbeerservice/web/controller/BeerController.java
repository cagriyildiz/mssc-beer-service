package com.jurengis.msscbeerservice.web.controller;

import com.jurengis.msscbeerservice.service.BeerService;
import com.jurengis.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

  private final BeerService beerService;

  @GetMapping("/{beerId}")
  public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId) {
    BeerDto beerDto = beerService.getById(beerId);
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
