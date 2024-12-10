package com.alpha.core.core.beans;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class MultipleMultifieldHelper {
    private static final Logger log= LoggerFactory.getLogger(MultipleMultifieldHelper.class);
    private String bookname;
    private Date bookpublishedyear;
    private int noofcopies;

    public List<NestedMultifieldHelper> getBookeditions() {
        return bookeditions;
    }

    public void setBookeditions(List<NestedMultifieldHelper> bookeditions) {
        this.bookeditions = bookeditions;
    }

    List<NestedMultifieldHelper> bookeditions;
    public MultipleMultifieldHelper(Resource resource){
        try{
            if(StringUtils.isNotBlank(resource.getValueMap().get("bookname",String.class))){
                this.bookname=resource.getValueMap().get("bookname",String.class);
            }
            if(resource.getValueMap().get("bookpublishedyear",Date.class)!=null){
                this.bookpublishedyear=resource.getValueMap().get("bookpublishedyear",Date.class);
            }

            if(resource.getValueMap().get("noofcopies",Integer.class)!=null){
                this.noofcopies=resource.getValueMap().get("noofcopies",Integer.class);
            }
        } catch (Exception e) {
            log.info("\n Error Message: {}",e.getMessage());
        }

    }

    public String getBookname() {
        return bookname;
    }
    public Date getBookpublishedyear() {
        return bookpublishedyear;
    }

    public int getNoofcopies() {
        return noofcopies;
    }
}
