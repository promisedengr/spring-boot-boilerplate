package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Category;
import com.nichoshop.main.repository.CategoryRepository;
import com.nichoshop.main.model.schema.SpecificsSchema;
import com.nichoshop.main.model.schema.CategoryTreeSchema;

@Service
public class CategoryService {
   @Autowired
   CategoryRepository categoryRepository;

   public void createCategory(String name, Long parentId) {
      if (parentId > 0) {
         categoryRepository.findById(Long.valueOf(parentId))
               .orElseThrow(() -> new RuntimeException("Cannot find parent category with Id :" + parentId));
      }
      Category category = new Category();
      category.setName(name);
      category.setParentId(parentId);
      categoryRepository.save(category);
   }

   public List<Category> getAllCategorys() {
      List<Category> Categorys = new ArrayList<Category>();
      categoryRepository.findAll().forEach(Category -> Categorys.add(Category));
      return Categorys;
   }

   public Stack<CategoryTreeSchema> getAllTreeCategories() {
      List<Category> parentCategory = categoryRepository.findByParentId(0L);
      Stack<CategoryTreeSchema> allTreeCategories = new Stack<CategoryTreeSchema>();
      parentCategory.forEach(pc -> {
         allTreeCategories.push(this.allToTreeCategory(pc));
      });
      return allTreeCategories;
   }

   public CategoryTreeSchema allToTreeCategory(Category category) {
      CategoryTreeSchema categoryTreeSchema = new CategoryTreeSchema();
      categoryTreeSchema.setCategory(category);
      Stack<CategoryTreeSchema> categoryTreeList = new Stack<CategoryTreeSchema>();
      categoryTreeList.push(this.getChildTree(category));
      categoryTreeSchema.setChildren(categoryTreeList);
      return this.getChildTree(category);
   }

   public CategoryTreeSchema getChildTree(Category category) {
      CategoryTreeSchema categoryTreeSchema = new CategoryTreeSchema();
      categoryTreeSchema.setCategory(category);
      List<Category> childrenCategory = categoryRepository.findByParentId(category.getId());
      Stack<CategoryTreeSchema> categoryTreeList = new Stack<CategoryTreeSchema>();
      if (childrenCategory.size() > 0) {
         childrenCategory.stream().forEach(c -> {
            categoryTreeList.push(this.getChildTree(c));
         });
         categoryTreeSchema.setChildren(categoryTreeList);
      }
      return categoryTreeSchema;
   }

   public Stack<CategoryTreeSchema> getTwoTreeCategories(Long parentId) {
      List<Category> parentCategory = categoryRepository.findByParentId(parentId);
      Stack<CategoryTreeSchema> allTwoTreeCategories = new Stack<CategoryTreeSchema>();
      parentCategory.forEach(pc -> {
         allTwoTreeCategories.push(this.twoTreeCategory(pc));
      });

      return allTwoTreeCategories;

   }

   public CategoryTreeSchema twoTreeCategory(Category category) {
      CategoryTreeSchema categoryTreeSchema = new CategoryTreeSchema();
      categoryTreeSchema.setCategory(category);
      Stack<CategoryTreeSchema> childrenList = new Stack<CategoryTreeSchema>();
      List<Category> childrenCategory = categoryRepository.findByParentId(category.getId());
      if (childrenCategory.size() > 0) {
         childrenCategory.forEach(c -> {
            CategoryTreeSchema oneChild = new CategoryTreeSchema();
            oneChild.setCategory(c);
            childrenList.push(oneChild);
         });
      }
      categoryTreeSchema.setChildren(childrenList);
      return categoryTreeSchema;
   }

   public List<SpecificsSchema> getSpecificsList() {
      return categoryRepository.findSpecifics();
   }

   public List<String> getConditionsList() {
      return categoryRepository.getConditionsList();
   }

   public Category getCategoryById(Long id) {
      return categoryRepository.findById(id).get();
   }

   public void saveOrUpdate(Category Category) {
      categoryRepository.save(Category);
   }

   public void deleteCategoryById(Long id) {
      categoryRepository.deleteById(id);
   }

   public void moveCategory(Long parentId, Long childId) {
      Category category = categoryRepository.findById(Long.valueOf(childId))
            .orElseThrow(() -> new RuntimeException("Cannot find parent category with Id :" + childId));
      category.setParentId(parentId);
      categoryRepository.save(category);
   }

   public void renameCategoryById(Long categoryId, String name) {
      Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Cannot find parent category with Id :" + categoryId));
      category.setName(name);
      categoryRepository.save(category);
   }
}