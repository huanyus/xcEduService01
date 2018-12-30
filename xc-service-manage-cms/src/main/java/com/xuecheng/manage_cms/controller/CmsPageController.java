package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi{

    @Autowired
    private PageService pageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size")int size, QueryPageRequest queryPageRequest) {


        //测试
       /* QueryResult<CmsPage> queryresult=new QueryResult<>();
        List<CmsPage> list=new ArrayList<>();

        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryresult.setList(list);
        queryresult.setTotal(1);


        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryresult);

        return queryResponseResult;*/
        QueryResponseResult list = pageService.findList(page, size, queryPageRequest);

        return list;

    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {

        return pageService.add(cmsPage);
    }

    @Override
    @GetMapping("/findById/{id}")
    public CmsPage findById(@PathVariable String id) {
        return pageService.findById(id);
    }

    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable String id,@RequestBody CmsPage cmsPage) {

        return pageService.edit(id,cmsPage);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable String id) {
        return pageService.delete(id);
    }
}
