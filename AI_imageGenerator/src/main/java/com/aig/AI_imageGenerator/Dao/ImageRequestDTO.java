package com.aig.AI_imageGenerator.Dao;

import org.springframework.web.multipart.MultipartFile;

public class ImageRequestDTO {

	 private MultipartFile image;

	    public MultipartFile getImage() {
	        return image;
	    }

	    public void setImage(MultipartFile image) {
	        this.image = image;
	    }
	}
