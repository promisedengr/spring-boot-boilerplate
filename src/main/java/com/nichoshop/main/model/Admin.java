package com.nichoshop.main.model;

import javax.persistence.*;
import lombok.Data;
// import com.nichoshop.main.util.converter.ObjectByteConverter;

@Data
@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	Long id;
	String name;
	Long userId;

}
