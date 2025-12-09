package com.kata.cervezas.controller;

import com.kata.cervezas.dto.BreweryDTO;
import com.kata.cervezas.service.BreweryService;
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
@Tag(name = "Cerveceras", description = "API para consulta de cerveceras")
public class BreweryController {

    private final BreweryService breweryService;

    @Operation(summary = "Listar todas las cerveceras", description = "Obtiene una lista de todas las cerveceras disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cerveceras obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/breweries")
    public ResponseEntity<List<BreweryDTO>> getAllBreweries() {
        return ResponseEntity.ok(breweryService.findAll());
    }

    @Operation(summary = "Listar cerveceras paginadas", description = "Obtiene una lista paginada de cerveceras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/breweries/paginated")
    public ResponseEntity<Page<BreweryDTO>> getAllBreweriesPaginated(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(breweryService.findAllPaginated(pageable));
    }

    @Operation(summary = "Obtener información de paginación", description = "Devuelve el número total de cerveceras")
    @RequestMapping(value = "/breweries", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getBreweriesCount() {
        long count = breweryService.count();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(count));
        return ResponseEntity.ok().headers(headers).build();
    }

    @Operation(summary = "Obtener cervecera por ID", description = "Obtiene los detalles de una cervecera específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cervecera encontrada",
                    content = @Content(schema = @Schema(implementation = BreweryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cervecera no encontrada")
    })
    @GetMapping("/brewerie/{id}")
    public ResponseEntity<BreweryDTO> getBreweryById(
            @Parameter(description = "ID de la cervecera") @PathVariable Integer id) {
        return ResponseEntity.ok(breweryService.findById(id));
    }
}
