package com.sourcecode.inventoryservice.service;

import com.sourcecode.inventoryservice.dto.InventoryResponse;
import com.sourcecode.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

//    the idea is that we want to find the inventory with the list of sku-codes given
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){

//        inside this method, we'll need to query the inventory, so we inject the repository
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()).toList();
        // we create this method(findBySkuCodeIn) in the repository interface
        // and as the method is returning List of inventory we map the List coming in as a response
        // for each inventory we create a inventoryResponse object, we get the skuCode from the inventory
        // and map it to the inventoryResponse, also the isInStock will reference the quantity, if the quantity
        // is greater than 0 it means it's in stock

        // after building the inventoryResponse object as a return variable we convert it to List type

        // and as the method is returning optional we simply use the isPresent method to check if the product is in stock
    }
}
