package com.rest.project.controller;

import com.rest.project.dto.CreateLandmarkDto;
import com.rest.project.exception.LandmarkExistsException;
import com.rest.project.exception.ResourceNotFoundException;
import com.rest.project.service.LandmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landmarks")
public class LandmarkController {

    private final LandmarkService attractionService;

    @Autowired
    public LandmarkController(LandmarkService attractionService) {
        this.attractionService = attractionService;
    }

    /**
     * Handles POST requests to create a new landmark.
     * @param attractionDto represents the value that will add to database
     *
     * @return ResponseEntity<String> with message and response status
     */
    @PostMapping
    public ResponseEntity<String> save(@RequestBody CreateLandmarkDto attractionDto){
        try {
            attractionService.saveLandmark(attractionDto);
            return new ResponseEntity<>("Landmark saved successfully", HttpStatus.OK);
        } catch (LandmarkExistsException e) {
            return new ResponseEntity<>("Landmark is already exists", HttpStatus.CONFLICT);
        }
    }

    /**
     * Handles GET requests to retrieve landmarks. Can retrieve all landmarks or filtered by type,
     * ordered in asc or desc order.
     *
     * @param order represents the order of sorting landmarks
     * @param type represents the type of landmark in which landmarks will filter
     *
     * @return ResponseEntity<?> if order is correct, else BAD_REQUEST
     */
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value="order", required=false) String order,
            @RequestParam(value="type", required=false) String type
    ){
        if(order == null || order.equals("desc") || order.equals("asc")) {
            return new ResponseEntity<>(attractionService.getAll(order, type), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Param order can be only 'asc' or 'desc'", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles GET requests to retrieve landmarks. Can retrieve all landmarks from locality.
     *
     * @param localityName represents the locality of landmarks
     *
     * @return List<CreateLandmarkDto> represents localities
     */
    @GetMapping("/locality")
    public List<CreateLandmarkDto> getAll(@RequestParam(value="localityName") String localityName){
        return attractionService.getLandmarkByLocalityName(localityName);
    }

    /**
     * Handles PATCH requests to update an existing landmark by ID.
     * @param id the ID of updating entity
     * @param attractionDto the object with new entity information
     *
     * @return ResponseEntity<String> with message and response status
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Integer id, @RequestBody CreateLandmarkDto attractionDto){
        try {
            attractionService.updateLandmark(attractionDto, id);
            return new ResponseEntity<>("Landmark successfully updated", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            String message = String.format("Landmark with id: %d not found", id);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles DELETE requests to remove a landmark by ID.
     * @param id the ID of deleting entity
     *
     * @return ResponseEntity<String> with message and response status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        try {
            attractionService.deleteLandmark(id);
            return new ResponseEntity<>("Landmark was successfully deleted", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            String message = String.format("Landmark with id: %d not found", id);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

}
