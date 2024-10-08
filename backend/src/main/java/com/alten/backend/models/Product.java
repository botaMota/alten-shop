package com.alten.backend.models;


import com.alten.backend.enums.InventoryStatus;

import javax.persistence.*;

@Entity
@Table(name="product")
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String code;
	
	private String name;

	private String description;

	private String image;

	private String category;

	private Double price;

	private Integer quantity;

	@Column(name = "internalReference" )
	private String internalReference;

	private Integer shellId;

	@Column(name = "inventoryStatus" )
	@Enumerated(EnumType.STRING)
	private InventoryStatus inventoryStatus;

	private Integer rating;
	@Column(name = "createdAt" )
	private Double createdAt;
	@Column(name = "updatedAt" )
	private Double updatedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getInternalReference() {
		return internalReference;
	}

	public void setInternalReference(String internalReference) {
		this.internalReference = internalReference;
	}

	public Integer getShellId() {
		return shellId;
	}

	public void setShellId(Integer shellId) {
		this.shellId = shellId;
	}

	public InventoryStatus getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(InventoryStatus inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Double getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Double createdAt) {
		this.createdAt = createdAt;
	}

	public Double getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Double updatedAt) {
		this.updatedAt = updatedAt;
	}
}
