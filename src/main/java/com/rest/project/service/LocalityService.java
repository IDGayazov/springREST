package com.rest.project.service;

import com.rest.project.dao.LandmarkDao;
import com.rest.project.dao.LocalityDao;
import com.rest.project.dto.CreateLocalityDto;
import com.rest.project.dto.LocalityDto;
import com.rest.project.entity.Landmark;
import com.rest.project.entity.Locality;
import com.rest.project.exception.LocalityExistsException;
import com.rest.project.exception.ResourceNotFoundException;
import com.rest.project.mapper.LocalityListMapper;
import com.rest.project.mapper.LocalityMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing localities
 * */
@Service
@Transactional
public class LocalityService {

    private static final Logger logger = LogManager.getLogger();

    private final LocalityDao localityDao;
    private final LandmarkDao landmarkDao;
    private final LocalityMapper localityMapper;
    private final LocalityListMapper localityListMapper;

    @Autowired
    public LocalityService(LocalityDao localityDao,
                           LandmarkDao landmarkDao,
                           LocalityMapper localityMapper, LocalityListMapper localityListMapper) {
        this.localityDao = localityDao;
        this.landmarkDao = landmarkDao;
        this.localityMapper = localityMapper;
        this.localityListMapper = localityListMapper;
    }

    /**
     * Saves a new locality.
     *
     * @param localityDto the DTO containing the locality data
     * @throws LocalityExistsException if locality already in database
     */
    public void saveLocality(LocalityDto localityDto) throws LocalityExistsException{

        if(localityDao.getByName(localityDto.getName()) == null){

            Locality locality = localityMapper.toEntity(localityDto);

            if(locality.getLandmarks() != null){
                int len = locality.getLandmarks().size();
                for(int i = 0; i < len; ++i){
                    Landmark landmark = locality.getLandmarks().get(i);
                    Landmark savedLandmark = landmarkDao.getByName(landmark.getName());
                    if(savedLandmark != null){
                        locality.getLandmarks().set(i, savedLandmark);
                    }
                }
            }
            logger.info("Saving locality with name: {}", localityDto.getName());
            localityDao.save(locality);
        }else{
            logger.error("Locality with name: {} is already exist", localityDto.getName());
            throw new LocalityExistsException();
        }

    }

    /**
     * Updates a locality.
     *
     * @param localityDto the DTO containing the updated locality data
     * @param id the ID of the locality to update
     */
    public void updateLocality(CreateLocalityDto localityDto, Integer id) throws ResourceNotFoundException {
        logger.info("Updating landmark with id: {}", id);
        localityDao.update(localityMapper.toEntity(localityDto), id);
    }

    /**
     * Gets all localities.
     *
     * @return a list of CreateLocalityDto objects representing all localities
     */
    public List<CreateLocalityDto> getAll(){
        logger.info("Fetching all localities");
        return localityListMapper.toDTOList(localityDao.getAll());
    }
}
