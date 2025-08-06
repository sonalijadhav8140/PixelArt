package com.aig.AI_imageGenerator.controller;

// BgRemoveController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.aig.AI_imageGenerator.Service.RemoveBgService;

@RestController
@CrossOrigin("*") //CrossOrigin is the annotation,Its is usefull for the connection with frontend and backend
                  //It allows backend to accept requests from the different ports.
                 
@RequestMapping("/api")   
public class ImageController {

    @Autowired
    private RemoveBgService removeBgService;

    @PostMapping("/remove-bg")
    public ResponseEntity<byte[]> removeBg(@RequestParam("file") MultipartFile file) {
        try {
            byte[] resultImage = removeBgService.removeBackground(file);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(resultImage, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
