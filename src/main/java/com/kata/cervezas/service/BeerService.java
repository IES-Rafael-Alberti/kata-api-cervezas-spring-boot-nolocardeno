package com.kata.cervezas.service;

import com.kata.cervezas.dto.BeerCreateDTO;
import com.kata.cervezas.dto.BeerDTO;
import com.kata.cervezas.dto.BeerUpdateDTO;
import com.kata.cervezas.entity.Beer;
import com.kata.cervezas.exception.ResourceNotFoundException;
import com.kata.cervezas.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;

    public List<BeerDTO> findAll() {
        return beerRepository.findAll().stream()
                .map(this::convertToDTOSimple)
                .collect(Collectors.toList());
    }

    public Page<BeerDTO> findAllPaginated(Pageable pageable) {
        return beerRepository.findAll(pageable)
                .map(this::convertToDTOSimple);
    }

    public BeerDTO findById(Integer id) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cerveza", id));
        return convertToDTO(beer);
    }

    public long count() {
        return beerRepository.count();
    }

    @Transactional
    public BeerDTO create(BeerCreateDTO createDTO) {
        Beer beer = new Beer();
        beer.setBreweryId(createDTO.getBreweryId() != null ? createDTO.getBreweryId() : 0);
        beer.setName(createDTO.getName());
        beer.setCatId(createDTO.getCatId() != null ? createDTO.getCatId() : 0);
        beer.setStyleId(createDTO.getStyleId() != null ? createDTO.getStyleId() : 0);
        beer.setAbv(createDTO.getAbv() != null ? createDTO.getAbv() : 0f);
        beer.setIbu(createDTO.getIbu() != null ? createDTO.getIbu() : 0f);
        beer.setSrm(createDTO.getSrm() != null ? createDTO.getSrm() : 0f);
        beer.setUpc(createDTO.getUpc() != null ? createDTO.getUpc() : 0L);
        beer.setDescript(createDTO.getDescript() != null ? createDTO.getDescript() : "");
        beer.setFilepath("");
        beer.setAddUser(0);
        beer.setLastMod(LocalDateTime.now());
        
        Beer savedBeer = beerRepository.save(beer);
        return convertToDTO(savedBeer);
    }

    @Transactional
    public BeerDTO update(Integer id, BeerUpdateDTO updateDTO) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cerveza", id));
        
        if (updateDTO.getBreweryId() != null) {
            beer.setBreweryId(updateDTO.getBreweryId());
        }
        if (updateDTO.getName() != null) {
            beer.setName(updateDTO.getName());
        }
        if (updateDTO.getCatId() != null) {
            beer.setCatId(updateDTO.getCatId());
        }
        if (updateDTO.getStyleId() != null) {
            beer.setStyleId(updateDTO.getStyleId());
        }
        if (updateDTO.getAbv() != null) {
            beer.setAbv(updateDTO.getAbv());
        }
        if (updateDTO.getIbu() != null) {
            beer.setIbu(updateDTO.getIbu());
        }
        if (updateDTO.getSrm() != null) {
            beer.setSrm(updateDTO.getSrm());
        }
        if (updateDTO.getUpc() != null) {
            beer.setUpc(updateDTO.getUpc());
        }
        if (updateDTO.getDescript() != null) {
            beer.setDescript(updateDTO.getDescript());
        }
        beer.setLastMod(LocalDateTime.now());
        
        Beer savedBeer = beerRepository.save(beer);
        return convertToDTO(savedBeer);
    }

    @Transactional
    public BeerDTO partialUpdate(Integer id, BeerUpdateDTO updateDTO) {
        // El método es el mismo que update, solo actualiza los campos no nulos
        return update(id, updateDTO);
    }

    @Transactional
    public void delete(Integer id) {
        if (!beerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cerveza", id);
        }
        beerRepository.deleteById(id);
    }

    @Transactional
    public BeerDTO updateImagePath(Integer id, String imagePath) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cerveza", id));
        beer.setFilepath(imagePath);
        beer.setLastMod(LocalDateTime.now());
        Beer savedBeer = beerRepository.save(beer);
        return convertToDTO(savedBeer);
    }

    // Conversión simple sin relaciones (para listas)
    private BeerDTO convertToDTOSimple(Beer beer) {
        BeerDTO dto = new BeerDTO();
        dto.setId(beer.getId());
        dto.setBreweryId(beer.getBreweryId());
        dto.setName(beer.getName());
        dto.setCatId(beer.getCatId());
        dto.setStyleId(beer.getStyleId());
        dto.setAbv(beer.getAbv());
        dto.setIbu(beer.getIbu());
        dto.setSrm(beer.getSrm());
        dto.setUpc(beer.getUpc());
        dto.setFilepath(beer.getFilepath());
        dto.setDescript(beer.getDescript());
        return dto;
    }

    // Conversión completa con relaciones (para consulta individual)
    private BeerDTO convertToDTO(Beer beer) {
        BeerDTO dto = convertToDTOSimple(beer);
        
        // Información de relaciones (con manejo seguro de errores)
        try {
            if (beer.getBrewery() != null) {
                dto.setBreweryName(beer.getBrewery().getName());
            }
        } catch (Exception e) {
            dto.setBreweryName(null);
        }
        try {
            if (beer.getCategory() != null) {
                dto.setCategoryName(beer.getCategory().getCatName());
            }
        } catch (Exception e) {
            dto.setCategoryName(null);
        }
        try {
            if (beer.getStyle() != null) {
                dto.setStyleName(beer.getStyle().getStyleName());
            }
        } catch (Exception e) {
            dto.setStyleName(null);
        }
        
        return dto;
    }
}
