package com.nichoshop.main.request;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import com.nichoshop.main.model.schema.SpecificsSchema;

@Data
public class CategoryUpdateRequest {
    private List<String> conditions = new ArrayList<String>();
    private List<SpecificsSchema> specifics = new ArrayList<SpecificsSchema>();
    
}
