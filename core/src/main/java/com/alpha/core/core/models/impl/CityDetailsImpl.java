package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.CityDetails;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables= SlingHttpServletRequest.class,adapters=CityDetails.class)
public class CityDetailsImpl implements CityDetails {
    @ValueMapValue
    String city;
    @ValueMapValue
    String cityCode;
    @ScriptVariable
    Page currentPage;
    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCityCode() {
        return cityCode;
    }

    @Override
    public String getPageTitle() {
        return currentPage.getTitle();
    }
}
