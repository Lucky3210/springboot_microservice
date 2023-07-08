package com.sourcecode.orderservice.service;

import com.sourcecode.orderservice.dto.InventoryResponse;
import com.sourcecode.orderservice.dto.OrderLineItemsDto;
import com.sourcecode.orderservice.dto.OrderRequest;
import com.sourcecode.orderservice.model.Order;
import com.sourcecode.orderservice.model.OrderLineItems;
import com.sourcecode.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

//    we inject the orderRepository into the service to enable saving to the db
    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest){
//        we create an object of type Order
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
/*
 next we map the OrderLineItemsDto coming in from the orderRequest to the OrderLineItems.
 In detail, our orderRequest coming in and in our orderRequest class, we are referencing a orderLineItemsDto class
 so here we want to map the orderRequest(orderLineItemsDto) directly to orderLineItems class.
*/
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        /* for inventory service communication we get all skuCodes from the order object */
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

/*
        call Inventory Service, and place order if product is in stock
        since the inventory service is a making a get request we use the get method and then retrieve
        the get data, to read the data from the webclient response we use the bodyToMono method and
        since we are returning a InventoryResponse as an array from the inventory we pass in the InventoryResponse.class.
        By default, webclient makes async request, but we want to make a sync request, so we add .block()
        after introducing local variable we want to check if the result is true or not(available or not)
        if its available we save the order in the db
*/
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

//        boolean allProductsInStock = true;
//        for (InventoryResponse inventoryResponse : inventoryResponseArray != null ? inventoryResponseArray : new InventoryResponse[0]) {
//            if (!inventoryResponse.isInStock()) {
//                allProductsInStock = false;
//                break;
//            }
//        }

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

//        we got the list of inventoryResponse array and convert it to a stream, and we are calling the allMatch
//        method, it will check whether the isInStock variable is true in the array or not. if all the variables
//        are true it returns true, then even if one is false it returns false for everything

        /* we save the order to the db */
        if (allProductsInStock){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in Stock, please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
