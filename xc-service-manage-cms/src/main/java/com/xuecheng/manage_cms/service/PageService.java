package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    /**
     * 分业查询
     * @param page 页码，从1开始
     * @param size 每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){


        //自定义查询条件
        if (queryPageRequest==null){
            queryPageRequest=new QueryPageRequest();//让对象不为空
        }

        CmsPage cmsPage = new CmsPage();

        //common包中判断不为空
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }

        if (StringUtils.isNotEmpty(queryPageRequest.getPageId())){

            cmsPage.setPageId(queryPageRequest.getPageId());
        }

        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){

            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        //条件匹配器
        ExampleMatcher matching = ExampleMatcher.matching();
        //这个字段包含匹配
        matching=matching.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());


        if (page<=0){
            page=1;
        }
        page=page-1;

        if (size<=0){
            size=10;
        }

        //分业
        Pageable pageable= PageRequest.of(page,size);

        //条件模版
        Example<CmsPage> example=Example.of(cmsPage,matching);

        //按条件模版和分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);

        //获取返回值并设置进入响应实体类
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult=new QueryResponseResult(CommonCode.SUCCESS,queryResult);



        return queryResponseResult;
    }


    /**
     * 添加
     * @param cmsPage
     * @return
     */
    public CmsPageResult add(CmsPage cmsPage){

        //要判断库里数据是否存在,数据库中设置索引唯一,自定义索引查询
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndPageWebPathAndSiteId(cmsPage.getPageName(), cmsPage.getPageWebPath(), cmsPage.getSiteId());

        if (cmsPage1!=null){
            //页面已经存在,则抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        //没有重复数据，就新增

        //数据库中主键自增,所有此类中的主键不能有数据
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);




    }

    /**
     * 根据id查询一个
     * @param id
     * @return
     */
    public CmsPage findById(String id) {


        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }

        return null;

    }

    /**
     * 修改
     * @param cmsPage
     * @return
     */
    public CmsPageResult edit(String id,CmsPage cmsPage) {
        //根据id查询,不为空则修改
        CmsPage one = this.findById(id);
        if (one!=null){

            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());

            //执行更新

            CmsPage save = cmsPageRepository.save(one);
            return new CmsPageResult(CommonCode.SUCCESS,save);

        }

        //无数据,报错误
        return new CmsPageResult(CommonCode.FAIL,null);

    }

    /**
     * 删除一个
     * @param id
     * @return
     */
    public ResponseResult delete(String id) {
        //先看有没有,再来删除
        CmsPage one = this.findById(id);
        if (one!=null){
            cmsPageRepository.deleteById(one.getPageId());
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);

    }


    /**
     * 通过id查询config
     * @param id
     * @return
     */
    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    public CmsConfig getConfigById(String id) {

        Optional<CmsConfig> byId = cmsConfigRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }

        return null;
    }
}
