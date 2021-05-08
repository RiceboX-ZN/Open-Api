package com.school.good.elasticsearch.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class EsProductRelateInfo implements Serializable {
    private List<String> brandNames;
    private List<String> categoryNames;

}
