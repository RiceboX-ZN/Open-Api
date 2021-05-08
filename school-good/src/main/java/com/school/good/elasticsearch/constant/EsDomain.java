package com.school.good.elasticsearch.constant;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class EsDomain {

    @Field(type = FieldType.Long)
    protected final Long dataVersion = 1L;

    @Field(type = FieldType.Long)
    protected final Long createdBy = -1L;

    @Field(type = FieldType.Long)
    protected final Long lastUpdatedBy = -1L;

    @Field(type = FieldType.Date,format = DateFormat.date_time)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected Date creationDateTime;


    @Field(type = FieldType.Date,format = DateFormat.date_time)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected final Date lastUpdatedDateTime = new Date();

    public EsDomain() {
    }
}
