package com.example.demo;
import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    
   public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	 @Transient
	    public String getProductImage() {
	        if (productImage == null || id == null) return null;
	         
	        return "/product-photos/" + id + "/" + productImage;
	    }
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

@Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    
   @Column(nullable = true, unique = false)
   private String email;
    
   @Column(name = "product_name", nullable = false)
   private String productName;
   
   @Column(name = "product_image", nullable = true)
   private String productImage;
   
   @Column(name = "product_price", nullable = false)
   private String productPrice;
   
   @Column(name = "product_details", nullable = false)
   private String productDetail;

}