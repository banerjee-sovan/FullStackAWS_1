package com.codedecode.restuarantlisting.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codedecode.restuarantlisting.dto.RestaurantDTO;
import com.codedecode.restuarantlisting.entity.Restaurant;
import com.codedecode.restuarantlisting.mapper.RestaurantMapper;
import com.codedecode.restuarantlisting.repository.RestaurantRepository;



@Service
public class RestaurantService {

	 @Autowired
	 private RestaurantRepository restaurantRepo;
	 

	    public List<RestaurantDTO> findAllRestaurants() {
	        List<Restaurant> restaurants = restaurantRepo.findAll();
	        List<RestaurantDTO> restaurantDTOList = restaurants.stream().map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant)).collect(Collectors.toList());
	        return restaurantDTOList;
	    }

	    public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
	        Restaurant savedRestaurant =  restaurantRepo.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));
	        return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(savedRestaurant);
	    }

	    public ResponseEntity<RestaurantDTO> fetchRestaurantById(Integer id) {
	        Optional<Restaurant> restaurant =  restaurantRepo.findById(id);
	        if(restaurant.isPresent()){
	            return new ResponseEntity<>(RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()), HttpStatus.OK);
	        }
	        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	    }
	
}
