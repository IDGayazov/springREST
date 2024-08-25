package com.rest.project.service;

import com.rest.project.dao.LandmarkDao;
import com.rest.project.dao.LocalityDao;
import com.rest.project.dao.ServiceDao;
import com.rest.project.dto.CreateLandmarkDto;
import com.rest.project.entity.Landmark;
import com.rest.project.entity.LandmarkType;
import com.rest.project.entity.Locality;
import com.rest.project.exception.LandmarkExistsException;
import com.rest.project.exception.ResourceNotFoundException;
import com.rest.project.mapper.LandmarkListMapper;
import com.rest.project.mapper.LandmarkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing landmarks
 * */
@Service
@Transactional
public class LandmarkService {

    private static final Logger logger = LogManager.getLogger();

    private final LandmarkDao landmarkDao;
    private final LandmarkMapper landmarkMapper;
    private final LandmarkListMapper landmarkListMapper;
    private final LocalityDao localityDao;
    private final ServiceDao serviceDao;

    @Autowired
    public LandmarkService(LandmarkDao attractionDao, LandmarkMapper attractionMapper,
                           LandmarkListMapper attractionListMapper, LocalityDao localityDao,
                           ServiceDao serviceDao) {
        this.landmarkDao = attractionDao;
        this.landmarkMapper = attractionMapper;
        this.landmarkListMapper = attractionListMapper;
        this.localityDao = localityDao;
        this.serviceDao = serviceDao;
    }

    /**
     * Saves a new landmark.
     *
     * @param landmarkDto the DTO containing the landmark data
     * @throws LandmarkExistsException if landmark already in database
     */
    public void saveLandmark(CreateLandmarkDto landmarkDto) throws LandmarkExistsException {

        if(landmarkDao.getByName(landmarkDto.getName()) != null){
            logger.error("Landmark with name: {} already exists", landmarkDto.getName());
            throw new LandmarkExistsException();
        }

        Landmark landmark = landmarkMapper.toEntity(landmarkDto);
        Locality locality = localityDao.getByName(landmark.getLocality().getName());

        if(locality != null){
            landmark.setLocality(locality);
        }

        List<com.rest.project.entity.Service> services = landmark.getServices();
        if(services != null){
            int len = services.size();
            for(int i = 0; i < len; ++i){
                com.rest.project.entity.Service service = services.get(i);
                com.rest.project.entity.Service existsService = serviceDao.getByName(service.getName());
                if(existsService != null){
                    services.set(i, existsService);
                }
            }
        }

        logger.info("Landmark with name {} added to database", landmarkDto.getName());
        landmarkDao.save(landmark);
    }

    /**
     * Gets all landmarks.
     *
     * @param sortOrder represents order of sorting landmarks by name ("asc", "desc")
     * @param filterType represents type of landmark for filtering landmarks
     *
     * @return a list of CreateLandmarkDto objects representing all landmarks
     */
    public List<CreateLandmarkDto> getAll(String sortOrder, String filterType){

        List<CreateLandmarkDto> result = landmarkListMapper.toDTOList(landmarkDao.getAll());

        if(sortOrder == null){

            if(filterType != null){
                logger.info("Fetching landmarks filtered with type {}", filterType);
                return result.stream()
                        .filter(attractionDto -> attractionDto.getLandmarkType()
                                .equals(LandmarkType.valueOf(filterType)))
                        .collect(Collectors.toList());
            }

            return result;
        }

        if(filterType == null){
            if(sortOrder.equals("asc")) {
                logger.info("Fetching landmarks sorted by asc order");
                result = result.stream()
                        .sorted(Comparator.comparing(CreateLandmarkDto::getName))
                        .collect(Collectors.toList());
            }
            if(sortOrder.equals("desc")){
                logger.info("Fetching landmarks sorted by desc order");
                result = result.stream()
                        .sorted(Comparator.comparing(CreateLandmarkDto::getName, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
            }
        }else{
            if(sortOrder.equals("asc")) {
                logger.info("Fetching landmarks sorted by asc order and with type {}", filterType);
                result = result.stream()
                        .filter(attractionDto -> attractionDto.getLandmarkType()
                                .equals(LandmarkType.valueOf(filterType)))
                        .sorted(Comparator.comparing(CreateLandmarkDto::getName))
                        .collect(Collectors.toList());
            }
            if(sortOrder.equals("desc")){
                logger.info("Fetching landmarks sorted by desc order and with type {}", filterType);
                result = result.stream()
                        .filter(attractionDto -> attractionDto.getLandmarkType()
                                .equals(LandmarkType.valueOf(filterType)))
                        .sorted(Comparator.comparing(CreateLandmarkDto::getName, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
            }
        }

        return result;
    }

    /**
     * Updates a landmark.
     *
     * @param landmarkDto the DTO containing the updated landmark data
     * @param id the ID of the landmark to update
     */
    public void updateLandmark(CreateLandmarkDto landmarkDto, Integer id) throws ResourceNotFoundException {
        logger.info("Updating landmark with id: {}", id);
        landmarkDao.update(landmarkMapper.toEntity(landmarkDto), id);
    }

    /**
     * Gets all landmarks with localityName.
     *
     * @param localityName the name of locality from which landmarks are searched
     * @return a list of CreateLandmarkDto objects representing landmarks
     */
    public List<CreateLandmarkDto> getLandmarkByLocalityName(String localityName){
        logger.info("Fetching landmarks with localityName: {}", localityName);
        return landmarkListMapper.toDTOList(landmarkDao.getAttractionByLocality(localityName));
    }

    /**
     * Deletes a landmark by their ID.
     *
     * @param id the ID of the landmark to delete
     */
    public void deleteLandmark(Integer id) throws ResourceNotFoundException {
        logger.info("Deleting landmark with id: {}", id);
        landmarkDao.delete(id);
    }
}
