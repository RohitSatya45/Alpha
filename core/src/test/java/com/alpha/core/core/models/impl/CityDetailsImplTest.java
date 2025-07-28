package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.CityDetails;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(AemContextExtension.class)
class CityDetailsImplTest {
    private final AemContext context = new AemContext();
    private CityDetails cityDetails;

    @BeforeEach
    void setUp() {
        Map<String, Object> pageProps = new HashMap<>();
        pageProps.put("jcr:title", "Hello world");
        context.create().page("/content/page", "/apps/components/sample", pageProps);
        context.currentPage("/content/page");
        context.create().resource("/content/page/component", "city", "chennai",
                "cityCode", "chn");
        context.currentResource("/content/page/component");
        context.addModelsForClasses(CityDetailsImpl.class);
        cityDetails = context.request().adaptTo(CityDetails.class);
    }

    @Test
    void getCity() {
        assertNotNull(cityDetails);
        assertEquals("chennai", cityDetails.getCity());

    }


    @Test
    void getCityCode() {
        assertNotNull(cityDetails);
        assertEquals("chn", cityDetails.getCityCode());
    }

    @Test
    void getPageTitle() {
        assertNotNull(cityDetails);
        assertEquals("Hello world", cityDetails.getPageTitle());


    }
}
