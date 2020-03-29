package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.ProductItem;
import com.tms.v1.repository.ProductItemRepository;
import com.tms.v1.repository.search.ProductItemSearchRepository;
import com.tms.v1.service.ProductItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductItemResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductItemResourceIT {

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFAULT_QTY = 1;
    private static final Integer UPDATED_DEFAULT_QTY = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductItemService productItemService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.ProductItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductItemSearchRepository mockProductItemSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductItemMockMvc;

    private ProductItem productItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductItem createEntity(EntityManager em) {
        ProductItem productItem = new ProductItem()
            .itemName(DEFAULT_ITEM_NAME)
            .description(DEFAULT_DESCRIPTION)
            .defaultQty(DEFAULT_DEFAULT_QTY)
            .price(DEFAULT_PRICE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return productItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductItem createUpdatedEntity(EntityManager em) {
        ProductItem productItem = new ProductItem()
            .itemName(UPDATED_ITEM_NAME)
            .description(UPDATED_DESCRIPTION)
            .defaultQty(UPDATED_DEFAULT_QTY)
            .price(UPDATED_PRICE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return productItem;
    }

    @BeforeEach
    public void initTest() {
        productItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductItem() throws Exception {
        int databaseSizeBeforeCreate = productItemRepository.findAll().size();

        // Create the ProductItem
        restProductItemMockMvc.perform(post("/api/product-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productItem)))
            .andExpect(status().isCreated());

        // Validate the ProductItem in the database
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeCreate + 1);
        ProductItem testProductItem = productItemList.get(productItemList.size() - 1);
        assertThat(testProductItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testProductItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductItem.getDefaultQty()).isEqualTo(DEFAULT_DEFAULT_QTY);
        assertThat(testProductItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductItem.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProductItem.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductItem.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testProductItem.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the ProductItem in Elasticsearch
        verify(mockProductItemSearchRepository, times(1)).save(testProductItem);
    }

    @Test
    @Transactional
    public void createProductItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productItemRepository.findAll().size();

        // Create the ProductItem with an existing ID
        productItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductItemMockMvc.perform(post("/api/product-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productItem)))
            .andExpect(status().isBadRequest());

        // Validate the ProductItem in the database
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductItem in Elasticsearch
        verify(mockProductItemSearchRepository, times(0)).save(productItem);
    }


    @Test
    @Transactional
    public void getAllProductItems() throws Exception {
        // Initialize the database
        productItemRepository.saveAndFlush(productItem);

        // Get all the productItemList
        restProductItemMockMvc.perform(get("/api/product-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].defaultQty").value(hasItem(DEFAULT_DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getProductItem() throws Exception {
        // Initialize the database
        productItemRepository.saveAndFlush(productItem);

        // Get the productItem
        restProductItemMockMvc.perform(get("/api/product-items/{id}", productItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productItem.getId().intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.defaultQty").value(DEFAULT_DEFAULT_QTY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingProductItem() throws Exception {
        // Get the productItem
        restProductItemMockMvc.perform(get("/api/product-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductItem() throws Exception {
        // Initialize the database
        productItemService.save(productItem);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockProductItemSearchRepository);

        int databaseSizeBeforeUpdate = productItemRepository.findAll().size();

        // Update the productItem
        ProductItem updatedProductItem = productItemRepository.findById(productItem.getId()).get();
        // Disconnect from session so that the updates on updatedProductItem are not directly saved in db
        em.detach(updatedProductItem);
        updatedProductItem
            .itemName(UPDATED_ITEM_NAME)
            .description(UPDATED_DESCRIPTION)
            .defaultQty(UPDATED_DEFAULT_QTY)
            .price(UPDATED_PRICE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restProductItemMockMvc.perform(put("/api/product-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductItem)))
            .andExpect(status().isOk());

        // Validate the ProductItem in the database
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeUpdate);
        ProductItem testProductItem = productItemList.get(productItemList.size() - 1);
        assertThat(testProductItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testProductItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductItem.getDefaultQty()).isEqualTo(UPDATED_DEFAULT_QTY);
        assertThat(testProductItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductItem.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProductItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductItem.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testProductItem.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the ProductItem in Elasticsearch
        verify(mockProductItemSearchRepository, times(1)).save(testProductItem);
    }

    @Test
    @Transactional
    public void updateNonExistingProductItem() throws Exception {
        int databaseSizeBeforeUpdate = productItemRepository.findAll().size();

        // Create the ProductItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductItemMockMvc.perform(put("/api/product-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productItem)))
            .andExpect(status().isBadRequest());

        // Validate the ProductItem in the database
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductItem in Elasticsearch
        verify(mockProductItemSearchRepository, times(0)).save(productItem);
    }

    @Test
    @Transactional
    public void deleteProductItem() throws Exception {
        // Initialize the database
        productItemService.save(productItem);

        int databaseSizeBeforeDelete = productItemRepository.findAll().size();

        // Delete the productItem
        restProductItemMockMvc.perform(delete("/api/product-items/{id}", productItem.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductItem in Elasticsearch
        verify(mockProductItemSearchRepository, times(1)).deleteById(productItem.getId());
    }

    @Test
    @Transactional
    public void searchProductItem() throws Exception {
        // Initialize the database
        productItemService.save(productItem);
        when(mockProductItemSearchRepository.search(queryStringQuery("id:" + productItem.getId())))
            .thenReturn(Collections.singletonList(productItem));
        // Search the productItem
        restProductItemMockMvc.perform(get("/api/_search/product-items?query=id:" + productItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].defaultQty").value(hasItem(DEFAULT_DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
}
