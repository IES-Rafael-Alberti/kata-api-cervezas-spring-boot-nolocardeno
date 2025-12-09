package com.kata.cervezas.controller;

import com.kata.cervezas.dto.BeerCreateDTO;
import com.kata.cervezas.dto.BeerDTO;
import com.kata.cervezas.dto.BeerUpdateDTO;
import com.kata.cervezas.service.BeerService;
import com.kata.cervezas.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Cervezas", description = "API para gestión de cervezas")
public class BeerController {

    private final BeerService beerService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "Listar todas las cervezas", description = "Obtiene una lista de todas las cervezas disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cervezas obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/beers")
    public ResponseEntity<List<BeerDTO>> getAllBeers() {
        return ResponseEntity.ok(beerService.findAll());
    }

    @Operation(summary = "Listar cervezas paginadas", description = "Obtiene una lista paginada de cervezas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/beers/paginated")
    public ResponseEntity<Page<BeerDTO>> getAllBeersPaginated(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(beerService.findAllPaginated(pageable));
    }

    @Operation(summary = "Obtener información de paginación", description = "Devuelve el número total de cervezas (útil para HEAD requests)")
    @RequestMapping(value = "/beers", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getBeersCount() {
        long count = beerService.count();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(count));
        return ResponseEntity.ok().headers(headers).build();
    }

    @Operation(summary = "Obtener cerveza por ID", description = "Obtiene los detalles de una cerveza específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza encontrada",
                    content = @Content(schema = @Schema(implementation = BeerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada")
    })
    @GetMapping("/beer/{id}")
    public ResponseEntity<BeerDTO> getBeerById(
            @Parameter(description = "ID de la cerveza") @PathVariable Integer id) {
        return ResponseEntity.ok(beerService.findById(id));
    }

    @Operation(summary = "Crear nueva cerveza", description = "Añade una nueva cerveza a la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cerveza creada correctamente",
                    content = @Content(schema = @Schema(implementation = BeerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping("/beer")
    public ResponseEntity<BeerDTO> createBeer(
            @Valid @RequestBody BeerCreateDTO createDTO) {
        BeerDTO createdBeer = beerService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBeer);
    }

    @Operation(summary = "Actualizar cerveza completamente", description = "Actualiza todos los campos de una cerveza existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/beer/{id}")
    public ResponseEntity<BeerDTO> updateBeer(
            @Parameter(description = "ID de la cerveza") @PathVariable Integer id,
            @RequestBody BeerUpdateDTO updateDTO) {
        return ResponseEntity.ok(beerService.update(id, updateDTO));
    }

    @Operation(summary = "Actualizar cerveza parcialmente", description = "Actualiza solo los campos proporcionados de una cerveza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza actualizada parcialmente"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada")
    })
    @PatchMapping("/beer/{id}")
    public ResponseEntity<BeerDTO> partialUpdateBeer(
            @Parameter(description = "ID de la cerveza") @PathVariable Integer id,
            @RequestBody BeerUpdateDTO updateDTO) {
        return ResponseEntity.ok(beerService.partialUpdate(id, updateDTO));
    }

    @Operation(summary = "Eliminar cerveza", description = "Elimina una cerveza de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cerveza eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada")
    })
    @DeleteMapping("/beer/{id}")
    public ResponseEntity<Void> deleteBeer(
            @Parameter(description = "ID de la cerveza") @PathVariable Integer id) {
        beerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Subir imagen de cerveza", description = "Sube una imagen para una cerveza específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen subida correctamente"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "400", description = "Archivo inválido")
    })
    @PostMapping(value = "/beer/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BeerDTO> uploadBeerImage(
            @Parameter(description = "ID de la cerveza") @PathVariable Integer id,
            @Parameter(description = "Archivo de imagen") @RequestParam("file") MultipartFile file) {
        
        // Verificar que la cerveza existe
        beerService.findById(id);
        
        // Guardar el archivo
        String filename = fileStorageService.storeFile(file, id);
        
        // Actualizar la ruta en la cerveza
        BeerDTO updatedBeer = beerService.updateImagePath(id, filename);
        
        return ResponseEntity.ok(updatedBeer);
    }

    @Operation(summary = "Obtener imagen de cerveza", description = "Descarga la imagen de una cerveza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrada")
    })
    @GetMapping(value = "/beer/{id}/image", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getBeerImage(
            @Parameter(description = "ID de la cerveza") @PathVariable Integer id) {
        
        BeerDTO beer = beerService.findById(id);
        
        if (beer.getFilepath() == null || beer.getFilepath().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        byte[] imageData = fileStorageService.loadFile(beer.getFilepath());
        
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }
}
