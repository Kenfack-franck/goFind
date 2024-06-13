package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Passenger;
import com.mycompany.myapp.repository.PassengerRepository;
import com.mycompany.myapp.service.PassengerService;
import com.mycompany.myapp.service.dto.PassengerDTO;
import com.mycompany.myapp.service.mapper.PassengerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Passenger}.
 */
@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final Logger log = LoggerFactory.getLogger(PassengerServiceImpl.class);

    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public PassengerDTO save(PassengerDTO passengerDTO) {
        log.debug("Request to save Passenger : {}", passengerDTO);
        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        passenger = passengerRepository.save(passenger);
        return passengerMapper.toDto(passenger);
    }

    @Override
    public PassengerDTO update(PassengerDTO passengerDTO) {
        log.debug("Request to update Passenger : {}", passengerDTO);
        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        passenger = passengerRepository.save(passenger);
        return passengerMapper.toDto(passenger);
    }

    @Override
    public Optional<PassengerDTO> partialUpdate(PassengerDTO passengerDTO) {
        log.debug("Request to partially update Passenger : {}", passengerDTO);

        return passengerRepository
            .findById(passengerDTO.getId())
            .map(existingPassenger -> {
                passengerMapper.partialUpdate(existingPassenger, passengerDTO);

                return existingPassenger;
            })
            .map(passengerRepository::save)
            .map(passengerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerDTO> findAll() {
        log.debug("Request to get all Passengers");
        return passengerRepository.findAll().stream().map(passengerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PassengerDTO> findOne(Long id) {
        log.debug("Request to get Passenger : {}", id);
        return passengerRepository.findById(id).map(passengerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Passenger : {}", id);
        passengerRepository.deleteById(id);
    }
}
