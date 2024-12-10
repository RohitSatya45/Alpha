package com.alpha.core.core.services.impl;
import com.alpha.core.core.config.OSGIConfigFields;
import com.alpha.core.core.config.OSGIFactoryConfigFields;
import com.alpha.core.core.services.OSGIFactoryConfig;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import java.util.ArrayList;
import java.util.List;
@Component(service= OSGIFactoryConfig.class,immediate=true)
@Designate(ocd= OSGIFactoryConfigFields.class, factory=true)
public class OSGIFactoryConfigImpl implements OSGIFactoryConfig {
    private int configId;
    private String configName;
    private String configURL;
    private List<OSGIFactoryConfig>configList;
    @Activate
    public void activate(OSGIFactoryConfigFields osgiFactoryConfigFields){
        configId= osgiFactoryConfigFields.configID();
        configName=osgiFactoryConfigFields.configName();
        configURL= osgiFactoryConfigFields.configURL();
    }
    @Reference(service=OSGIFactoryConfig.class,cardinality = ReferenceCardinality.MULTIPLE,policy = ReferencePolicy.DYNAMIC)
    public void bindOSGIFactoryConfig(OSGIFactoryConfig osgiFactoryConfig){
        if(configList==null){
            configList=new ArrayList<>();
        }
        configList.add(osgiFactoryConfig);
    }
    public void unbindOSGIFactoryConfig(OSGIFactoryConfig osgiFactoryConfig){
        configList.remove(osgiFactoryConfig);
    }
    @Override
    public int getConfigId() {
        return configId;
    }
    @Override
    public String getConfigName() {
        return configName;
    }
    @Override
    public String getConfigUrl() {
        return configURL;
    }
    @Override
    public List<OSGIFactoryConfig> getConfigList() {
        return configList;
    }
}
