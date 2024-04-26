package tyt.record.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "records")
@Entity
public class RecordEntity extends BaseEntity implements Serializable {

    private LocalDateTime recordDate;
    private int quantity;
    private Long productId;
    private String productName;
    private double price;
    private Long orderId;
    private Long orderProductId;
    private Date orderDate;
}
