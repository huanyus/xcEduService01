package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Test
    public void TestFindAll(){

        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);


    }

    /**
     * 分业查询
     */
    @Test
    public void TestFindAllPage(){

        //分业参数
        int page=0;//从0开始
        int size=10;
        Pageable pageable= PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);


    }
    /**
     * 修改
     */
    @Test
    public void TestUpdate(){

        //查询
        Optional<CmsPage> byId = cmsPageRepository.findById("5abefd525b05aa293098fca6");

        if (byId.isPresent()){
            //修改

            CmsPage cmsPage = byId.get();
            cmsPage.setPageAliase("testname");

            CmsPage save = cmsPageRepository.save(cmsPage);
            System.out.println(save);
        }

        //保存
    }
    /**
     * 条件查询
     */
    @Test
    public void TestFindAllPageExample(){

        //分业参数
        int page=0;//从0开始
        int size=10;
        Pageable pageable= PageRequest.of(page,size);

        //条件值对象
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");//精确匹配站点值

        cmsPage.setPageAliase("轮播");

        //条件匹配器
        ExampleMatcher matching = ExampleMatcher.matching();
        //模糊匹配,别名字段包含条件字段
        matching  = matching.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());


        //定义example
        Example<? extends CmsPage> example = Example.of(cmsPage,matching);
        Page<? extends CmsPage> all = cmsPageRepository.findAll(example, pageable);

        List<? extends CmsPage> content = all.getContent();
        System.out.println(content);


    }

    /**
     * 条件查询
     */
    @Test
    public void TestFindAllPageExample1(){

        //分页参数
        Pageable pageable = PageRequest.of(0, 10);

        //条件值
        CmsPage cmsPage = new CmsPage();
        //站点ID
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //模板ID
        cmsPage.setTemplateId("5aec5dd70e661808240ab7a6");
//        cmsPage.setPageAliase("分类导航");
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching(); //.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //页面别名模糊查询，需要自定义字符串的匹配器实现模糊查询
//        ExampleMatcher.GenericPropertyMatchers.contains() //包含
//        ExampleMatcher.GenericPropertyMatchers.startsWith() //开头匹配
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);


    }

}
