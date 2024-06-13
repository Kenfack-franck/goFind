package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Carpool;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CarpoolRepositoryWithBagRelationships {
    Optional<Carpool> fetchBagRelationships(Optional<Carpool> carpool);

    List<Carpool> fetchBagRelationships(List<Carpool> carpools);

    Page<Carpool> fetchBagRelationships(Page<Carpool> carpools);
}
