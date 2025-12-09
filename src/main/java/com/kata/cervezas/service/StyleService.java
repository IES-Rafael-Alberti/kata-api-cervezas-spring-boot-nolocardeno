package com.kata.cervezas.service;

import com.kata.cervezas.dto.StyleDTO;
import com.kata.cervezas.entity.Style;
import com.kata.cervezas.exception.ResourceNotFoundException;
import com.kata.cervezas.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StyleService {

    private final StyleRepository styleRepository;

    public List<StyleDTO> findAll() {
        return styleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<StyleDTO> findAllPaginated(Pageable pageable) {
        return styleRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public StyleDTO findById(Integer id) {
        Style style = styleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estilo", id));
        return convertToDTO(style);
    }

    public long count() {
        return styleRepository.count();
    }

    private StyleDTO convertToDTO(Style style) {
        StyleDTO dto = new StyleDTO();
        dto.setId(style.getId());
        dto.setCatId(style.getCatId());
        dto.setStyleName(style.getStyleName());
        if (style.getCategory() != null) {
            dto.setCategoryName(style.getCategory().getCatName());
        }
        return dto;
    }
}
