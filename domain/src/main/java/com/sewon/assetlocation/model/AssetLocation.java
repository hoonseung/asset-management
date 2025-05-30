package com.sewon.assetlocation.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.affiliation.model.Affiliation;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE asset_location SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetLocationDeletedFilter", autoEnabled = true)
@Filter(name = "assetLocationDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "asset_location")
@Entity
public class AssetLocation extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "location", length = 50, nullable = false)
    private String location;

    @ManyToOne(targetEntity = Affiliation.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "affiliation_id", nullable = false)
    private Affiliation affiliation;


    public static AssetLocation of(String location, Affiliation affiliation) {
        return new AssetLocation(null, location, affiliation);
    }

    public String getCorporation() {
        return this.affiliation.getCorporation();
    }

    public String getDepartment() {
        return this.affiliation.getDepartment();
    }

    public boolean isEqualLocation(String location) {
        return this.location.equals(location);
    }
}
