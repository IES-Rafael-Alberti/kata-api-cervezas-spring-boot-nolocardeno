package com.kata.cervezas.service;

import com.kata.cervezas.dto.CategoryDTO;
import com.kata.cervezas.entity.Category;
import com.kata.cervezas.exception.ResourceNotFoundException;
import com.kata.cervezas.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<CategoryDTO> findAllPaginated(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public CategoryDTO findById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
        return convertToDTO(category);
    }

    public long count() {
        return categoryRepository.count();
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setCatName(category.getCatName());
        return dto;
    }
}
