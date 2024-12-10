package com.alpha.core.core.models.impl;

import com.alpha.core.core.beans.MultipleMultifieldHelper;
import com.alpha.core.core.beans.NestedMultifieldHelper;
import com.alpha.core.core.models.MultipleMultifield;
import com.alpha.core.core.models.NestedMultifield;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,adapters = NestedMultifield.class)
public class NestedMultifieldImpl implements NestedMultifield{
    private static final Logger Log = LoggerFactory.getLogger(NestedMultifieldImpl.class);
    @Inject
    Resource resource;

    @Override
    public List<MultipleMultifieldHelper> getAllBookDetails() {
        List<MultipleMultifieldHelper>nestedmulti=new ArrayList<>();
        try{
            Resource outernested=resource.getChild("authorbooksdetails");
            if(outernested!=null){
                for(Resource outernestednode: outernested.getChildren()){
                    MultipleMultifieldHelper multipleMultifieldHelper=new MultipleMultifieldHelper(outernestednode);
                    if(outernestednode.hasChildren()){
                        List<NestedMultifieldHelper>nestedinner=new ArrayList<>();
                        Resource innernested=outernestednode.getChild("bookeditions");
                        for(Resource innernestednode : innernested.getChildren()){
                            nestedinner.add(new NestedMultifieldHelper(innernestednode));
                        }
                        multipleMultifieldHelper.setBookeditions(nestedinner);
                    }
                    nestedmulti.add(multipleMultifieldHelper);
                }
            }
        } catch (Exception e) {
            Log.info("\n Error Message : {} ",e.getMessage());
        }
        return nestedmulti;
    }
}
