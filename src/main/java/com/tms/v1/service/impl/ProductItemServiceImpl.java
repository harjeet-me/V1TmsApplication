package com.tms.v1.service.impl;

import com.tms.v1.service.ProductItemService;
import com.tms.v1.domain.ProductItem;
import com.tms.v1.repository.ProductItemRepository;
import com.tms.v1.repository.search.ProductItemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProductItem}.
 */
@Service
@Transactional
public class ProductItemServiceImpl implements ProductItemService {

    private final Logger log = LoggerFactory.getLogger(ProductItemServiceImpl.class);

    private final ProductItemRepository productItemRepository;

    private final ProductItemSearchRepository productItemSearchRepository;

    public ProductItemServiceImpl(ProductItemRepository productItemRepository, ProductItemSearchRepository productItemSearchRepository) {
        this.productItemRepository = productItemRepository;
        this.productItemSearchRepository = productItemSearchRepository;
    }

    /**
     * Save a productItem.
     *
     * @param productItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductItem save(ProductItem productItem) {
        log.debug("Request to save ProductItem : {}", productItem);
        ProductItem result = productItemRepository.save(productItem);
        productItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the productItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductItem> findAll() {
        log.debug("Request to get all ProductItems");
        return productItemRepository.findAll();
    }

    /**
     * Get one productItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductItem> findOne(Long id) {
        log.debug("Request to get ProductItem : {}", id);
        return productItemRepository.findById(id);
    }

    /**
     * Delete the productItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductItem : {}", id);
        productItemRepository.deleteById(id);
        productItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the productItem corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductItem> search(String query) {
        log.debug("Request to search ProductItems for query {}", query);
        return StreamSupport
            .stream(productItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
