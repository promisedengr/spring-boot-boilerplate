package com.nichoshop.main.controller;

import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nichoshop.main.model.Category;
import com.nichoshop.main.service.CategoryService;
import com.nichoshop.main.model.schema.CategoryTreeSchema;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {
   @Autowired
   CategoryService categoryService;

   @GetMapping("/top")
   public ResponseEntity<?> getTopCategoryList() {
      return new ResponseEntity<>(categoryService.getTwoTreeCategories(0L), HttpStatus.OK);
   }

   @GetMapping("/all")
   public ResponseEntity<?> getAllTreeCategories() {

      return new ResponseEntity<>(categoryService.getAllTreeCategories(), HttpStatus.OK);
   }

   @GetMapping("/children/{parentId}")
   public ResponseEntity<?> getCategoryById(@PathVariable("parentId") Long parentId) {

      return new ResponseEntity<>(categoryService.getTwoTreeCategories(parentId), HttpStatus.OK);
   }

}
