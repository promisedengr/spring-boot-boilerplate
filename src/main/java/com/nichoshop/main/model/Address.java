package com.nichoshop.main.model;

import javax.persistence.*;
import lombok.Data;
// import com.nichoshop.main.util.converter.ObjectByteConverter;

@Data
@Entity
@Table(name = "addresses")
public class Address {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column private Long id;
	@Column private String name;
	@Column(name = "user_id") private Long userId;
	@Column private String address1;
	@Column(nullable = true) private String address2;
	@Column private String city;
	@Column(nullable = true) private String state;
	@Column private String zip;
	@Column private String country;
	@Column private String phone;
	@Column private int status;
	@Column(name = "address_type") private int addressType;
	@Column(name = "address_confirmed") private int addressConfirmed;

    // @Convert(converter = ObjectByteConverter.class) @Column private SignUpResponse signup;

	private Address() {
	}


	public Address(Long userId, String name, String address1, String address2, String city, String state, String zip, 
	String country, String phone, int status, int addressType, int addressConfirmed) {
		this.userId = userId;
		this.name = name;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.phone = phone;
		this.status = status;
		this.addressType = addressType;
		this.addressConfirmed = addressConfirmed;
	}
}
