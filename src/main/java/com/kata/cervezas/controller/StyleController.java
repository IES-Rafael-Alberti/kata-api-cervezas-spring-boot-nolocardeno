package com.kata.cervezas.controller;

import com.kata.cervezas.dto.StyleDTO;
import com.kata.cervezas.service.StyleService;
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
@Tag(name = "Estilos", description = "API para consulta de estilos de cerveza")
public class StyleController {

    private final StyleService styleService;

    @Operation(summary = "Listar todos los estilos", description = "Obtiene una lista de todos los estilos de cerveza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estilos obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/styles")
    public ResponseEntity<List<StyleDTO>> getAllStyles() {
        return ResponseEntity.ok(styleService.findAll());
    }

    @Operation(summary = "Listar estilos paginados", description = "Obtiene una lista paginada de estilos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/styles/paginated")
    public ResponseEntity<Page<StyleDTO>> getAllStylesPaginated(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(styleService.findAllPaginated(pageable));
    }

    @Operation(summary = "Obtener información de paginación", description = "Devuelve el número total de estilos")
    @RequestMapping(value = "/styles", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getStylesCount() {
        long count = styleService.count();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(count));
        return ResponseEntity.ok().headers(headers).build();
    }

    @Operation(summary = "Obtener estilo por ID", description = "Obtiene los detalles de un estilo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estilo encontrado",
                    content = @Content(schema = @Schema(implementation = StyleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estilo no encontrado")
    })
    @GetMapping("/style/{id}")
    public ResponseEntity<StyleDTO> getStyleById(
            @Parameter(description = "ID del estilo") @PathVariable Integer id) {
        return ResponseEntity.ok(styleService.findById(id));
    }
}
