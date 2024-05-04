package tyt.record.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class BaseEntityTest {

    @Test
    void idIsGenerated() {
        BaseEntity entity = new BaseEntity();
        assertNull(entity.getId(), "ID should be null before persisting");

        // Simulate persisting the entity
        entity.setId(1L);
        assertNotNull(entity.getId(), "ID should be generated after persisting");
    }

    @Test
    void idIsSerializable() {
        BaseEntity entity = new BaseEntity();
        entity.setId(1L);

        // Simulate serialization
        byte[] serializedEntity = serialize(entity);
        BaseEntity deserializedEntity = deserialize(serializedEntity);

        assert deserializedEntity != null;
        assertEquals(entity.getId(), deserializedEntity.getId(), "ID should be the same after serialization and deserialization");
    }

    // Helper methods for serialization and deserialization
    private byte[] serialize(BaseEntity entity) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(entity);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BaseEntity deserialize(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return (BaseEntity) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}