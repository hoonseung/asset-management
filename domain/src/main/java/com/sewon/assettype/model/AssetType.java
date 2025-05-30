package com.sewon.assettype.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.common.constant.NationType;
import com.sewon.common.converter.NationTypeConverter;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE asset_type SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "assetTypeDeletedFilter", autoEnabled = true)
@Filter(name = "assetTypeDeletedFilter", condition = "deleted_at IS NULL")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "asset_type")
@Entity
public class AssetType extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Setter
    @ManyToOne(targetEntity = AssetType.class, fetch = LAZY)
    @JoinColumn(name = "asset_type_id")
    private AssetType assetType;

    @BatchSize(size = 30)
    @OneToMany(mappedBy = "assetType")
    private final List<AssetType> assetTypes = new ArrayList<>();

    @Convert(converter = NationTypeConverter.class)
    @Column(name = "nation_type", nullable = false)
    private NationType nationType;

    private AssetType(Long id, String name, AssetType assetType, NationType nationType) {
        this.id = id;
        this.name = name;
        this.assetType = assetType;
        this.nationType = nationType;
    }

    public String getParentCategoryName() {
        return Objects.isNull(this.assetType) ? this.name : this.assetType.getName();
    }

    public String getChildCategoryName() {
        return Objects.nonNull(this.assetType) ? this.name : "";
    }

    public static AssetType of(String name, AssetType assetType, NationType nationType) {
        return new AssetType(null, name, assetType, nationType);
    }

    public static AssetType of(String name, NationType nationType) {
        return new AssetType(null, name, null, nationType);
    }
}
