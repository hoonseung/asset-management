package com.sewon.asset.model;


import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = PROTECTED)
@Getter
@DiscriminatorValue("ELECTRONIC")
@Table(name = "electronic_asset")
@Entity
public class ElectronicAsset extends Asset {

    @Column(name = "cpu", length = 50, nullable = false)
    private String cpu;

    @Column(name = "ram", columnDefinition = "SMALLINT", nullable = false)
    private Integer ram;

    @Column(precision = 6, scale = 3)
    private BigDecimal storage;

    @Column(name = "gpu", length = 50)
    private String gpu;


}
