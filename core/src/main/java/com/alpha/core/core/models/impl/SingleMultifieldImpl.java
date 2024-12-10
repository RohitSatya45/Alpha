package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.SingleMultifield;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, adapters = SingleMultifield.class)
public class SingleMultifieldImpl implements SingleMultifield {
    @ValueMapValue
    List<String>books;

    @ValueMapValue
    String authorname;

    @Override
    public String getAuthorName() {
        return authorname;
    }

    @Override
    public List<String> getBooks() {
        if(books!=null){
            return new ArrayList<String>(books);
        }else{
            return Collections.emptyList();
        }
    }
}
