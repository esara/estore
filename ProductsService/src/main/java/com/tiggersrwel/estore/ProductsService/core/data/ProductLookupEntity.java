package com.tiggersrwel.estore.ProductsService.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Persistent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="productlookup")
public class ProductLookupEntity implements Serializable {
    private static final Long serialVersionUID = -3L;

    @Id
    private String productId;
    @Column(unique = true)
    private String title;

}
