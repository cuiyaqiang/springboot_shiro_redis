package com.guye.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 城市实体类
 *
 * Created by bysocket on 07/02/2017.
 */
//@Document(indexName = "province", type = "city")
public class City {

    /**
     * 城市编号
     */
    private Long id;

    /**
     * 省份编号
     */
    private Long provinceId;

    /**
     * 城市名称
     */
    @NotEmpty
    private String cityName;

    /**
     * 描述
     */
    private String description;

    private Long enterpriseid;

    private Pic pic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEnterpriseid() {
        return enterpriseid;
    }

    public void setEnterpriseid(Long enterpriseid) {
        this.enterpriseid = enterpriseid;
    }

    public Pic getPic() {
        return pic;
    }

    public void setPic(Pic pic) {
        this.pic = pic;
    }
}
