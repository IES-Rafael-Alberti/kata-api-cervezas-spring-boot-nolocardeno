package com.kata.cervezas.service;

import com.kata.cervezas.dto.BreweryDTO;
import com.kata.cervezas.entity.Brewery;
import com.kata.cervezas.exception.ResourceNotFoundException;
import com.kata.cervezas.repository.BreweryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreweryService {

    private final BreweryRepository breweryRepository;

    public List<BreweryDTO> findAll() {
        return breweryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<BreweryDTO> findAllPaginated(Pageable pageable) {
        return breweryRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public BreweryDTO findById(Integer id) {
        Brewery brewery = breweryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cervecera", id));
        return convertToDTO(brewery);
    }

    public long count() {
        return breweryRepository.count();
    }

    private BreweryDTO convertToDTO(Brewery brewery) {
        BreweryDTO dto = new BreweryDTO();
        dto.setId(brewery.getId());
        dto.setName(brewery.getName());
        dto.setAddress1(brewery.getAddress1());
        dto.setAddress2(brewery.getAddress2());
        dto.setCity(brewery.getCity());
        dto.setState(brewery.getState());
        dto.setCode(brewery.getCode());
        dto.setCountry(brewery.getCountry());
        dto.setPhone(brewery.getPhone());
        dto.setWebsite(brewery.getWebsite());
        dto.setFilepath(brewery.getFilepath());
        dto.setDescript(brewery.getDescript());
        return dto;
    }
}
