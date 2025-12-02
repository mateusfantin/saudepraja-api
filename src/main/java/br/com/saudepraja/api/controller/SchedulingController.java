package br.com.saudepraja.api.controller;

import br.com.saudepraja.domain.model.entity.order.SchedulingDTO;
import br.com.saudepraja.service.SchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @Operation(summary = "Create a new Scheduling authenticated", description = "Create a new Scheduling authenticated")
    @PostMapping(consumes = "application/json", produces = "application/json")
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



}
