package com.jurengis.msscbeerservice.service.inventory;

import java.util.UUID;

public interface BeerInventoryService {

  Integer getOnHandInventory(UUID beerId);
}
