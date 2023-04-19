package com.nichoshop.main.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;

import com.nichoshop.main.response.BasicResponse;
import com.nichoshop.main.service.CategoryService;
import com.nichoshop.main.request.CategoryUpdateRequest;
import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.Category;

@RestController
@RequestMapping(path = "/admin/category")
public class AdminCategoryController {
    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/parent/{parentId}")
    public ResponseEntity<?> CreateCategory(@RequestParam("name") String name,
            @PathVariable("parentId") Long parentId) {

        categoryService.createCategory(name, parentId);

        // response.
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("A category created");
        return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllTreeCategories() {

        return new ResponseEntity<>(categoryService.getAllTreeCategories(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/top")
    public ResponseEntity<?> getTopTwoCategories() {
        return new ResponseEntity<>(categoryService.getTwoTreeCategories(0L), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/conditions")
    public ResponseEntity<?> getConditionsList() {
        return new ResponseEntity<>(categoryService.getConditionsList(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/specifics")
    public ResponseEntity<?> getSpecificsList() {
        return new ResponseEntity<>(categoryService.getSpecificsList(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/children/{parentId}")
    public ResponseEntity<?> getTwoCategories(@PathVariable("parentId") Long parentId) {
        return new ResponseEntity<>(categoryService.getTwoTreeCategories(parentId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/parent/{parentId}/{childId}")
    public ResponseEntity<?> moveCategory(@PathVariable("parentId") Long parentId,
            @PathVariable("childId") Long childId) {

        categoryService.moveCategory(parentId, childId);

        // response.
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("category moved.");
        return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategoryById(@PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryUpdateRequest categoryUpdateRequest) {

        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {

            category.setConditions(categoryUpdateRequest.getConditions());
            category.setSpecifics(categoryUpdateRequest.getSpecifics());

            categoryService.saveOrUpdate(category);

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Category updated.");
            return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("Category not found", HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("categoryId") Long categoryId) {

        categoryService.deleteCategoryById(categoryId);

        // resopnse.
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("Category removed.");
        return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/rename/{categoryId}/{name}")
    public ResponseEntity<?> renameCategory(@PathVariable("categoryId") Long categoryId,
            @PathVariable("name") String name) {

        categoryService.renameCategoryById(categoryId, name);

        // response.
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("Category renamed.");
        return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.OK);
    }

}
