package br.com.saudepraja.api.controller;

import br.com.saudepraja.domain.model.entity.order.SchedulingDTO;
import br.com.saudepraja.service.SchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @PreAuthorize("has ROLE('ROLE_CUSTOMER')")
    @Operation(summary = "Create a new Scheduling user authenticated", description = "Create a new Scheduling user authenticated")
    @PostMapping(path = "/users/authenticated", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Invalid body"),
            @ApiResponse(responseCode = "201", description = "Created")
    })
    public ResponseEntity<SchedulingDTO> schedulingAuthenticatedUser(
            @Parameter(description = "SchedulingDTO", required = true)
            @RequestBody @Valid SchedulingDTO schedulingDTO) {

        schedulingService.save(schedulingDTO);

        URI uriResponse = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/")
                .buildAndExpand(schedulingDTO)
                .toUri();

        return ResponseEntity.created(uriResponse).build();
    }

    @Operation(summary = "Create a new Scheduling user unauthenticated", description = "Create a new Scheduling user unauthenticated")
    @PostMapping(path = "/users/unauthenticated", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Invalid body"),
            @ApiResponse(responseCode = "201", description = "Created")
    })
    public ResponseEntity<SchedulingDTO> scheduling(
            @Parameter(description = "SchedulingDTO", required = true)
            @RequestBody @Valid SchedulingDTO schedulingDTO) {

        schedulingService.save(schedulingDTO);

        URI uriResponse = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/")
                .buildAndExpand(schedulingDTO)
                .toUri();

        return ResponseEntity.created(uriResponse).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @Operation(summary = "Upload files", description = "Upload files")
    @PutMapping(path = "/{schedulingId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Error"),
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    public ResponseEntity<Void> upload(
            @Parameter(name = "schedulingId", description = "Id of scheduling", required = true)
            @PathVariable(name = "schedulingId") final Long schedulingId,
            @RequestParam("file") MultipartFile file) {

        schedulingService.upload(schedulingId, file);
        return ResponseEntity.ok().build();
    }

}
