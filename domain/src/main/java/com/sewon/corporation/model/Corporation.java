package com.sewon.corporation.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.affiliation.model.Affiliation;
import com.sewon.common.constant.NationType;
import com.sewon.common.converter.NationTypeConverter;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;


@SQLDelete(sql = "UPDATE corporation SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "corporationDeletedFilter", autoEnabled = true)
@Filter(name = "corporationDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@Table(name = "corporation")
public class Corporation extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Convert(converter = NationTypeConverter.class)
    @Column(name = "nation_type", nullable = false)
    private NationType nationType;

    @Filter(name = "corporationDeletedFilter", condition = "deleted_at IS NULL")
    @OneToMany(mappedBy = "corporation")
    private List<Affiliation> affiliations = new ArrayList<>();

    public Corporation(Long id, String name, NationType nationType) {
        this.id = id;
        this.name = name;
        this.nationType = nationType;
    }

    public static Corporation of(String name, NationType nationType) {
        return new Corporation(null, name, nationType);
    }

    public String getNationType() {
        return this.nationType.name();
    }
}
