package com.sourcecode.inventoryservice;

import com.sourcecode.inventoryservice.model.Inventory;
import com.sourcecode.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	since we have no instance of data in the db, here we're creating some inventory and populating it
//	into the db. That's being done with a bean
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(100);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone_13_red");
			inventory1.setQuantity(0);

//			we save the inventory to the db

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);

		};
	}

}
