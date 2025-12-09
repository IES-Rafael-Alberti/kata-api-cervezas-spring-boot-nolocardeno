package com.kata.cervezas.controller;

import com.kata.cervezas.dto.CategoryDTO;
import com.kata.cervezas.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "API para consulta de categorías de cerveza")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Listar todas las categorías", description = "Obtiene una lista de todas las categorías de cerveza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(summary = "Listar categorías paginadas", description = "Obtiene una lista paginada de categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/categories/paginated")
    public ResponseEntity<Page<CategoryDTO>> getAllCategoriesPaginated(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAllPaginated(pageable));
    }

    @Operation(summary = "Obtener información de paginación", description = "Devuelve el número total de categorías")
    @RequestMapping(value = "/categories", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getCategoriesCount() {
        long count = categoryService.count();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(count));
        return ResponseEntity.ok().headers(headers).build();
    }

    @Operation(summary = "Obtener categoría por ID", description = "Obtiene los detalles de una categoría específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/categorie/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "ID de la categoría") @PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
}
