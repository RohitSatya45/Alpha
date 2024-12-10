package com.alpha.core.core.models;


import com.day.cq.wcm.api.Page;

import java.util.Iterator;
import java.util.List;

public interface ServiceModel {

    public Iterator<Page> getPagesList();
    public List<String> getPagesTitle();
    public String getAuthorNamea();
    public String getAuthorNameb();
    public String getName();
}
