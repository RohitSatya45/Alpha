package com.alpha.core.core.services;

import java.util.List;

public interface OSGIFactoryConfig {
    int getConfigId();
    String getConfigName();
    String getConfigUrl();
    List<OSGIFactoryConfig> getConfigList();
}
