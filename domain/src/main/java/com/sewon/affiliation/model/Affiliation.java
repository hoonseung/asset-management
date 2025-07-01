package com.sewon.affiliation.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.assetlocation.exception.LocationErrorCode;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.common.exception.DomainException;
import com.sewon.common.model.BaseTime;
import com.sewon.corporation.model.Corporation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE affiliation SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "affiliationDeletedFilter", autoEnabled = true)
@Filter(name = "affiliationDeletedFilter", condition = "deleted_at IS NULL")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "affiliation", uniqueConstraints = @UniqueConstraint(name = "uk_affiliation_corporation_department"
    , columnNames = {"corporation_id", "department"}))
@Entity
public class Affiliation extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @ManyToOne(targetEntity = Corporation.class, optional = false, fetch = LAZY)
    @JoinColumn(name = "corporation_id", nullable = false)
    private Corporation corporation;

    @Setter
    @Column(name = "department", length = 50, nullable = false)
    private String department;

    @Filter(name = "affiliationDeletedFilter", condition = "deleted_at IS NULL")
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "affiliation")
    private final List<AssetLocation> assetLocations = new ArrayList<>();

    public Affiliation(Long id, Corporation corporation, String department) {
        this.id = id;
        this.corporation = corporation;
        this.department = department;
    }

    public static Affiliation of(Corporation corporation, String department) {
        return new Affiliation(null, corporation, department);
    }

    public String getCorporationName() {
        return corporation.getName();
    }

    public AssetLocation findLocation(String location) {
        for (AssetLocation assetLocation : assetLocations) {
            if (assetLocation.getLocation().equals(location)) {
                return assetLocation;
            }
        }
        throw new DomainException(LocationErrorCode.LOCATION_NOT_FOUND);
    }
}
