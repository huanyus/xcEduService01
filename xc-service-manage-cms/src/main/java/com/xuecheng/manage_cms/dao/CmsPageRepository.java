package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage,String>{


    //自定义查询
    CmsPage findByPageNameAndPageWebPathAndSiteId(String pageName,String pageWebPath,String siteId);


}
