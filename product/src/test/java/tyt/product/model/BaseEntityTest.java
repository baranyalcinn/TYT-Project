package tyt.product.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the BaseEntity class.
 */
class BaseEntityTest {

    // Instance of BaseEntity for testing
    private BaseEntity baseEntity;

    /**
     * This method is executed before each test. It initializes the baseEntity instance.
     */
    @BeforeEach
    void setUp() {
        baseEntity = new BaseEntity() {};
    }

    /**
     * Test to check if getId() returns null when id is not set.
     */
    @Test
    void getIdWhenNotSet() {
        assertNull(baseEntity.getId());
    }

    /**
     * Test to check if setId() and getId() work correctly.
     */
    @Test
    void setIdAndGetId() {
        baseEntity.setId(1L);
        assertEquals(1L, baseEntity.getId());
    }

    /**
     * Test to check if getCreatedAt() returns null when createdAt is not set.
     */
    @Test
    void getCreatedAtWhenNotSet() {
        assertNull(baseEntity.getCreatedAt());
    }

    /**
     * Test to check if setCreatedAt() and getCreatedAt() work correctly.
     */
    @Test
    void setCreatedAtAndGetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        baseEntity.setCreatedAt(now);
        assertEquals(now, baseEntity.getCreatedAt());
    }

    /**
     * Test to check if getCreatedBy() returns null when createdBy is not set.
     */
    @Test
    void getCreatedByWhenNotSet() {
        assertNull(baseEntity.getCreatedBy());
    }

    /**
     * Test to check if setCreatedBy() and getCreatedBy() work correctly.
     */
    @Test
    void setCreatedByAndGetCreatedBy() {
        baseEntity.setCreatedBy("user1");
        assertEquals("user1", baseEntity.getCreatedBy());
    }

    /**
     * Test to check if getUpdatedAt() returns null when updatedAt is not set.
     */
    @Test
    void getUpdatedAtWhenNotSet() {
        assertNull(baseEntity.getUpdatedAt());
    }

    /**
     * Test to check if setUpdatedAt() and getUpdatedAt() work correctly.
     */
    @Test
    void setUpdatedAtAndGetUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        baseEntity.setUpdatedAt(now);
        assertEquals(now, baseEntity.getUpdatedAt());
    }

    /**
     * Test to check if getUpdatedBy() returns null when updatedBy is not set.
     */
    @Test
    void getUpdatedByWhenNotSet() {
        assertNull(baseEntity.getUpdatedBy());
    }

    /**
     * Test to check if setUpdatedBy() and getUpdatedBy() work correctly.
     */
    @Test
    void setUpdatedByAndGetUpdatedBy() {
        baseEntity.setUpdatedBy("user2");
        assertEquals("user2", baseEntity.getUpdatedBy());
    }

    /**
     * Test to check if equals() works correctly when comparing the same object.
     */
    @Test
    void equalsWithSameObject() {
        assertEquals(baseEntity, baseEntity);
    }

    /**
     * Test to check if equals() works correctly when comparing different objects.
     */
    @Test
    void equalsWithDifferentObject() {
        BaseEntity otherEntity = new BaseEntity() {
        };
        otherEntity.setId(2L);
        otherEntity.setCreatedBy("user2");
        assertNotEquals(baseEntity, otherEntity);
    }

    /**
     * Test to check if hashCode() works correctly when comparing the same object.
     */
    @Test
    void hashCodeWithSameObject() {
        assertEquals(baseEntity.hashCode(), baseEntity.hashCode());
    }

    /**
     * Test to check if hashCode() works correctly when comparing different objects.
     */
    @Test
    void hashCodeWithDifferentObject() {
        BaseEntity otherEntity = new BaseEntity() {
        };
        otherEntity.setId(2L);
        otherEntity.setCreatedBy("user2");
        baseEntity.setId(1L);
        baseEntity.setCreatedBy("user1");
        assertNotEquals(baseEntity.hashCode(), otherEntity.hashCode());
    }
    /**
     * Test to check if toString() contains the class name.
     */
    @Test
    void toStringContainsClassName() {
        assertTrue(baseEntity.toString().contains("BaseEntity"));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getId();
        result = prime * result + ((baseEntity.getCreatedAt() == null) ? 0 : baseEntity.getCreatedAt().hashCode());
        result = prime * result + ((baseEntity.getCreatedBy() == null) ? 0 : baseEntity.getCreatedBy().hashCode());
        result = prime * result + ((baseEntity.getUpdatedAt() == null) ? 0 : baseEntity.getUpdatedAt().hashCode());
        result = prime * result + ((baseEntity.getUpdatedBy() == null) ? 0 : baseEntity.getUpdatedBy().hashCode());
        return result;
    }

    private int getId() {
        return 0;
    }


}