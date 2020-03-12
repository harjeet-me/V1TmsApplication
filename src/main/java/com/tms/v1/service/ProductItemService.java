package com.tms.v1.service;

import com.tms.v1.domain.ProductItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProductItem}.
 */
public interface ProductItemService {

    /**
     * Save a productItem.
     *
     * @param productItem the entity to save.
     * @return the persisted entity.
     */
    ProductItem save(ProductItem productItem);

    /**
     * Get all the productItems.
     *
     * @return the list of entities.
     */
    List<ProductItem> findAll();

    /**
     * Get the "id" productItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductItem> findOne(Long id);

    /**
     * Delete the "id" productItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productItem corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProductItem> search(String query);
}
