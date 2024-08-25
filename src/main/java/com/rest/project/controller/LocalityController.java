package com.rest.project.controller;

import com.rest.project.dto.CreateLocalityDto;
import com.rest.project.dto.LocalityDto;
import com.rest.project.exception.LocalityExistsException;
import com.rest.project.exception.ResourceNotFoundException;
import com.rest.project.service.LocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("localities")
public class LocalityController {

    private final LocalityService localityService;

    @Autowired
    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    /**
     * Handles GET requests to fetch all localities.
     */
    @GetMapping
    public List<CreateLocalityDto> getAllLocalities(){
        return localityService.getAll();
    }

    /**
     * Handles POST requests to create a new locality.
     * @param localityDto represents the value that will add to database
     *
     * @return ResponseEntity<String> with message and response status
     */
    @PostMapping
    public ResponseEntity<String> save(@RequestBody LocalityDto localityDto){
        try {
            localityService.saveLocality(localityDto);
            return new ResponseEntity<>("Locality saved successfully", HttpStatus.OK);
        } catch (LocalityExistsException e) {
            return new ResponseEntity<>("Locality is already exists",HttpStatus.CONFLICT);
        }
    }

    /**
     * Handles PATCH requests to update an existing locality by ID.
     * @param id the ID of updating entity
     * @param localityDto the object with new entity information
     *
     * @return ResponseEntity<String> with message and response status
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Integer id, @RequestBody CreateLocalityDto localityDto){
        try {
            localityService.updateLocality(localityDto, id);
            return new ResponseEntity<>("Locality was successfully updated", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            String message = String.format("Locality with id: %d not found", id);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

}
