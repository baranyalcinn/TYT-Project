package tyt.record.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.record.model.RecordEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDTO {

    private Long id;
    private LocalDateTime recordDate;
    private int quantity;
    private Long productId;
    private double price;
    private Long orderId;
    private Long orderProductId;
    private Date orderDate;

    public static RecordDTO fromEntity(RecordEntity recordEntity) {
        return RecordDTO.builder()
                .id(recordEntity.getId())
                .recordDate(recordEntity.getRecordDate())
                .quantity(recordEntity.getQuantity())
                .productId(recordEntity.getProductId())
                .price(recordEntity.getPrice())
                .orderId(recordEntity.getOrderId())
                .orderProductId(recordEntity.getOrderProductId())
                .orderDate(recordEntity.getOrderDate())
                .build();
    }

    public static RecordEntity toEntity(RecordDTO recordDTO) {
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setId(recordDTO.getId());
        recordEntity.setRecordDate(recordDTO.getRecordDate());
        recordEntity.setQuantity(recordDTO.getQuantity());
        recordEntity.setProductId(recordDTO.getProductId());
        recordEntity.setPrice(recordDTO.getPrice());
        recordEntity.setOrderId(recordDTO.getOrderId());
        recordEntity.setOrderProductId(recordDTO.getOrderProductId());
        recordEntity.setOrderDate(recordDTO.getOrderDate());
        return recordEntity;
    }

    public static List<RecordDTO> fromEntities(List<RecordEntity> records) {
        return records.stream().map(RecordDTO::fromEntity).toList();
    }
}