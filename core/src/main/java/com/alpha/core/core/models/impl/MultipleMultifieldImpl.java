package com.alpha.core.core.models.impl;

import com.alpha.core.core.beans.MultipleMultifieldHelper;
import com.alpha.core.core.models.MultipleMultifield;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class,adapters = MultipleMultifield.class)
public class MultipleMultifieldImpl implements MultipleMultifield{
    private static final Logger Log = LoggerFactory.getLogger(MultipleMultifieldImpl.class);
    @Inject
    Resource resource;

    @Override
    public List<Map<String, Object>> getBooksDetails() {
        List<Map<String,Object>>authorsbooksdetails=new ArrayList<>();
        Resource bookdetails=resource.getChild("authorbooksdetails");
        if(bookdetails!=null){
            for(Resource bookdetailsindividual: bookdetails.getChildren()){
                Map<String,Object>details=new HashMap<>();
                details.put("bookname",bookdetailsindividual.getValueMap().get("bookname",String.class));
                details.put("bookpublishedyear",bookdetailsindividual.getValueMap().get("bookpublishedyear", Date.class));
                details.put("noofcopies",bookdetailsindividual.getValueMap().get("noofcopies",Integer.class));
                authorsbooksdetails.add(details);
            }
        }
        Log.info("\n size {}", authorsbooksdetails.size());
        return authorsbooksdetails;
    }

    @Override
    public List<MultipleMultifieldHelper> getBooksDetailsBean() {
        List<MultipleMultifieldHelper>authorsbooksdetailsbean=new ArrayList<>();
        try{
            Resource bookdetailsbean=resource.getChild("authorbooksdetails");
            if(bookdetailsbean!=null){
                for(Resource bookdetailsindividualbean:bookdetailsbean.getChildren()){
                    authorsbooksdetailsbean.add(new MultipleMultifieldHelper(bookdetailsindividualbean));
                }
            }
        }catch(Exception e){
            Log.info("\n ErrorMessage ; {}",e.getMessage());
        }
        return authorsbooksdetailsbean;

    }
}
