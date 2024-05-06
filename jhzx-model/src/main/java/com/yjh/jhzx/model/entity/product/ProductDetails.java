package com.yjh.jhzx.model.entity.product;

import com.yjh.jhzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

    private Long productId;
    private String imageUrls;

}