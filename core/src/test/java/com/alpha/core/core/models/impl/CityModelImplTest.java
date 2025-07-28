package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.CityModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(AemContextExtension.class)
class CityModelImplTest {
    private static AemContext context=new AemContext();
    private CityModel cityModel;
    @BeforeEach
    void setUp() {
        context.create().resource("/content/page/component","city","hyderabad");
        cityModel=context.resourceResolver().getResource("/content/page/component").
                adaptTo(CityModel.class);

    }
    @Test
    void getCity() {
        assertNotNull(cityModel);
        assertEquals("hyderabad",cityModel.getCity());
    }
}