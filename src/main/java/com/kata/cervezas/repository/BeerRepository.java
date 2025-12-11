package com.kata.cervezas.repository;

import com.kata.cervezas.entity.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer> {
    
    List<Beer> findByBreweryId(Integer breweryId);
    
    List<Beer> findByCatId(Integer catId);
    
    List<Beer> findByStyleId(Integer styleId);
    
    List<Beer> findByNameContainingIgnoreCase(String name);
    
    Page<Beer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
