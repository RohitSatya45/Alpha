package com.alpha.core.core.beans;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class NestedMultifieldHelper {
    private String bookeditionname;
    private Date editionpublishedyear;
    private static final Logger log= LoggerFactory.getLogger(NestedMultifieldHelper.class);
    public NestedMultifieldHelper(Resource resource){
        try{
            if(StringUtils.isNotBlank(resource.getValueMap().get("bookeditionname",String.class))){
                this.bookeditionname=resource.getValueMap().get("bookeditionname",String.class);
            }
            if(resource.getValueMap().get("editionpublishedyear",Date.class)!=null){
                this.editionpublishedyear=resource.getValueMap().get("editionpublishedyear",Date.class);
            }
        } catch (Exception e) {
            log.info("\n Error Message: {}",e.getMessage());
        }

    }
    public String getBookeditionname() {
        return bookeditionname;
    }

    public Date getEditionpublishedyear() {
        return editionpublishedyear;
    }
}
