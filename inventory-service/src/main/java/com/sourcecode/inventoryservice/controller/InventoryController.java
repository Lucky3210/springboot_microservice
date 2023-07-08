package com.sourcecode.inventoryservice.controller;

import com.sourcecode.inventoryservice.dto.InventoryResponse;
import com.sourcecode.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
//   the controller or request get the skuCode as request parameter and this skuCode is of type List of string
//

//    how the request parameter will look like
//    http://localhost:8082/api/inventory?skuCode=iphone-13&sku-code=iphone-13-red
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){

        return inventoryService.isInStock(skuCode);
    }
}
