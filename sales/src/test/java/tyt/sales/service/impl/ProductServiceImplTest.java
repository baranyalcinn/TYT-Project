package tyt.sales.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.model.mapper.ProductMapper;
import tyt.sales.repository.ProductRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private static final ProductMapper productMapper = ProductMapper.INSTANCE;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_returnsAllProducts() {
        ProductEntity entity1 = new ProductEntity();
        ProductEntity entity2 = new ProductEntity();
        when(productRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        Iterable<ProductDTO> result = productService.getAllProducts();

        assertEquals(2, ((Collection<?>) result).size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProduct_returnsProduct_whenProductExists() {
        ProductEntity entity = new ProductEntity();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        ProductDTO result = productService.getProduct(1L);

        assertEquals(productMapper.fromEntity(entity), result);
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    void getProduct_returnsNull_whenProductDoesNotExist() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ProductDTO result = productService.getProduct(1L);

        assertNull(result);
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    void save_savesAndReturnsProduct() {
        ProductDTO dto = new ProductDTO();
        ProductEntity entity = productMapper.toEntity(dto);
        when(productRepository.save(any())).thenReturn(entity);

        ProductDTO result = productService.save(dto);

        assertEquals(productMapper.fromEntity(entity), result);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void findById_returnsProduct_whenProductExists() {
        ProductEntity entity = new ProductEntity();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        ProductDTO result = productService.findById(1L);

        assertEquals(productMapper.fromEntity(entity), result);
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    void findById_returnsNull_whenProductDoesNotExist() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ProductDTO result = productService.findById(1L);

        assertNull(result);
        verify(productRepository, times(1)).findById(anyLong());
    }
}