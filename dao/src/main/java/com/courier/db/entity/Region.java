package com.courier.db.entity;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "region")
public class Region extends BaseEntity {

    private static final long serialVersionUID = -820563627011572183L;
    private Long id;
    private String code;
    private String name;

    public Region() {
    }

    public Region(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<Object, String> getMap(List<Region> regions) {
        return getMap(regions, false);
    }

    public static Map<Object, String> getMap(List<Region> regions, boolean hasAll) {
        Map<Object, String> map = new LinkedHashMap<Object, String>();
        if (hasAll) map.put("", "全部");
        for (Region region : regions) {
            map.put(region.getCode(), region.getName());
        }
        return map;
    }
}
