package com.kata.cervezas.repository;

import com.kata.cervezas.entity.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreweryRepository extends JpaRepository<Brewery, Integer> {
    
    List<Brewery> findByNameContainingIgnoreCase(String name);
    
    List<Brewery> findByCountryIgnoreCase(String country);
    
    List<Brewery> findByCityIgnoreCase(String city);
}
