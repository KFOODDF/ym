package com.kaka.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaka.constant.CodeType;
import com.kaka.mapper.CategoriesMapper;
import com.kaka.model.Categories;
import com.kaka.service.CategoriesService;
import com.kaka.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 使用@Service注解，标记这是一个Spring的服务类
@Service
public class CategoriesServiceImpl implements CategoriesService {

    // 使用@Autowired注解，自动注入CategoriesMapper接口的实现类
    @Autowired
    CategoriesMapper categoriesMapper;

    // 实现接口中的getArticleCategories方法
    @Override
    public DataMap getArticleCategories() {
        // 从数据库中查询所有文章的分类
        List<Categories> cleCategories = categoriesMapper.getArticleCategories();

        // 初始化一个JSONObject对象，用于最终返回给前端的数据
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        // 遍历查询到的分类列表，将每个分类的信息封装到一个JSONObject中
        for (Categories category : cleCategories) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", category.getId());
            jsonObject.put("categoryName", category.getCategoryName());

            // 将封装好的JSONObject添加到JSONArray中
            jsonArray.add(jsonObject);
        }

        // 将JSONArray添加到最终的JSONObject中
        returnJson.put("result", jsonArray);

        // 返回成功的数据
        return DataMap.success().setData(returnJson);
    }

    // 实现接口中的updateCategory方法
    @Override
    public DataMap updateCategory(String categoryNum, int type) {
        // 从数据库中查询指定分类名称是否存在
        int isExistCategoryName = categoriesMapper.findIsExistByCategoryName(categoryNum);

        // 根据type参数判断是新增分类还是删除分类
        if (type == 1) {
            // type为1表示新增分类
            if (isExistCategoryName == 0) {
                // 如果分类不存在，则新增
                Categories categories = new Categories();
                categories.setCategoryName(categoryNum);
                categoriesMapper.saveCategories(categories);
                int newCategoryiesId = categoriesMapper.findIsExistByCategoryName(categoryNum);
                return DataMap.success(CodeType.ADD_CATEGORY_SUCCESS).setData(newCategoryiesId);
            }
        } else {
            // type不为1表示删除分类
            if (isExistCategoryName != 0) {
                // TODO: 考虑查询分类下是否存在文章
                // 如果分类存在，则删除
                categoriesMapper.deteleCategory(categoryNum);
                return DataMap.success(CodeType.DELETE_CATEGORY_SUCCESS);
            }
        }

        // 如果分类不存在，则返回失败的信息
        return DataMap.fail(CodeType.CATEGORY_NOT_EXIST);
    }

    // 实现接口中的findCategoriesNames方法
    @Override
    public DataMap findCategoriesNames() {
        // 从数据库中查询所有文章分类的名称
        List<String> categoryNames = categoriesMapper.findCategoriesNames();

        // 返回成功的数据
        return DataMap.success().setData(categoryNames);
    }
}
