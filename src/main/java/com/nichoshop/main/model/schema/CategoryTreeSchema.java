package com.nichoshop.main.model.schema;

import lombok.Data;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;


import com.nichoshop.main.model.Category;

@Data
public class CategoryTreeSchema {
    private Category category;
    private Stack<CategoryTreeSchema> children = new Stack<CategoryTreeSchema>();
}
