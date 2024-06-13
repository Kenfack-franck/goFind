package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Carpool;
import com.mycompany.myapp.repository.CarpoolRepository;
import com.mycompany.myapp.service.CarpoolService;
import com.mycompany.myapp.service.dto.CarpoolDTO;
import com.mycompany.myapp.service.mapper.CarpoolMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Carpool}.
 */
@Service
@Transactional
public class CarpoolServiceImpl implements CarpoolService {

    private final Logger log = LoggerFactory.getLogger(CarpoolServiceImpl.class);

    private final CarpoolRepository carpoolRepository;

    private final CarpoolMapper carpoolMapper;

    public CarpoolServiceImpl(CarpoolRepository carpoolRepository, CarpoolMapper carpoolMapper) {
        this.carpoolRepository = carpoolRepository;
        this.carpoolMapper = carpoolMapper;
    }

    @Override
    public CarpoolDTO save(CarpoolDTO carpoolDTO) {
        log.debug("Request to save Carpool : {}", carpoolDTO);
        Carpool carpool = carpoolMapper.toEntity(carpoolDTO);
        carpool = carpoolRepository.save(carpool);
        return carpoolMapper.toDto(carpool);
    }

    @Override
    public CarpoolDTO update(CarpoolDTO carpoolDTO) {
        log.debug("Request to update Carpool : {}", carpoolDTO);
        Carpool carpool = carpoolMapper.toEntity(carpoolDTO);
        carpool = carpoolRepository.save(carpool);
        return carpoolMapper.toDto(carpool);
    }

    @Override
    public Optional<CarpoolDTO> partialUpdate(CarpoolDTO carpoolDTO) {
        log.debug("Request to partially update Carpool : {}", carpoolDTO);

        return carpoolRepository
            .findById(carpoolDTO.getId())
            .map(existingCarpool -> {
                carpoolMapper.partialUpdate(existingCarpool, carpoolDTO);

                return existingCarpool;
            })
            .map(carpoolRepository::save)
            .map(carpoolMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarpoolDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Carpools");
        return carpoolRepository.findAll(pageable).map(carpoolMapper::toDto);
    }

    public Page<CarpoolDTO> findAllWithEagerRelationships(Pageable pageable) {
        return carpoolRepository.findAllWithEagerRelationships(pageable).map(carpoolMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarpoolDTO> findOne(Long id) {
        log.debug("Request to get Carpool : {}", id);
        return carpoolRepository.findOneWithEagerRelationships(id).map(carpoolMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Carpool : {}", id);
        carpoolRepository.deleteById(id);
    }
}
