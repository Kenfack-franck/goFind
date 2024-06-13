package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Carpool;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CarpoolRepositoryWithBagRelationshipsImpl implements CarpoolRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String CARPOOLS_PARAMETER = "carpools";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Carpool> fetchBagRelationships(Optional<Carpool> carpool) {
        return carpool.map(this::fetchPassengers);
    }

    @Override
    public Page<Carpool> fetchBagRelationships(Page<Carpool> carpools) {
        return new PageImpl<>(fetchBagRelationships(carpools.getContent()), carpools.getPageable(), carpools.getTotalElements());
    }

    @Override
    public List<Carpool> fetchBagRelationships(List<Carpool> carpools) {
        return Optional.of(carpools).map(this::fetchPassengers).orElse(Collections.emptyList());
    }

    Carpool fetchPassengers(Carpool result) {
        return entityManager
            .createQuery("select carpool from Carpool carpool left join fetch carpool.passengers where carpool.id = :id", Carpool.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Carpool> fetchPassengers(List<Carpool> carpools) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, carpools.size()).forEach(index -> order.put(carpools.get(index).getId(), index));
        List<Carpool> result = entityManager
            .createQuery("select carpool from Carpool carpool left join fetch carpool.passengers where carpool in :carpools", Carpool.class)
            .setParameter(CARPOOLS_PARAMETER, carpools)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
