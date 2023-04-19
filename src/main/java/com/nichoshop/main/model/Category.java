package com.nichoshop.main.model;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;

import com.nichoshop.main.util.converter.ObjectByteConverter;
import com.nichoshop.main.util.converter.StringListConverter;
import com.nichoshop.main.model.schema.SpecificsSchema;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private int leaf = 0;
    @Column(name = "parent_id")
    private Long parentId = 0L;
    @Column
    private int ord = 0;
    @Convert(converter = StringListConverter.class)
    @Column
    private List<String> conditions = new ArrayList<String>();
    @Convert(converter = ObjectByteConverter.class)
    @Column
    private List<SpecificsSchema> specifics = new ArrayList<SpecificsSchema>();

}
