package com.alpha.core.core.models.impl;

import com.adobe.granite.xss.XSSAPI;
import com.alpha.core.core.models.TextModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables= Resource.class,adapters = TextModel.class, defaultInjectionStrategy= DefaultInjectionStrategy.OPTIONAL)
public class TextModelImpl  implements TextModel {
    @ValueMapValue
    private String text;
    private String hText;
    private String plainText;
    @PostConstruct
    protected void init(){
        hText=text;
        plainText=text!=null?text.replaceAll("<[^>]*>", "").trim():"";
    }
    @Override
    public String getPlainText() {
        return plainText;
    }
    @Override
    public String getRawText() {
        return hText;
    }
}
