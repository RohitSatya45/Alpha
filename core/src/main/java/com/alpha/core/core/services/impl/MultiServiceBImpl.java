package com.alpha.core.core.services.impl;

import com.alpha.core.core.services.MultiService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

@Component(service= MultiService.class,immediate = true)
@ServiceRanking(1001)
public class MultiServiceBImpl implements MultiService {
    @Override
    public String getName() {
        return "MultiServiceBImpl";
    }
}
