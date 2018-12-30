package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;

@Api(value="config管理接口",description = "config管理接口，提供页面的增、删、改、查")
public interface CmsConfigContrllerApi {

    public CmsConfig getModel(String id);

}
