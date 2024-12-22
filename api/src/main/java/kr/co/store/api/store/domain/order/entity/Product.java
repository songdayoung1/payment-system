package kr.co.store.api.store.domain.order.entity;

import jakarta.persistence.*;
import kr.co.store.api.store.domain.common.BaseEntity;
import kr.co.store.api.store.domain.order.types.ProductStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean activated;


}
