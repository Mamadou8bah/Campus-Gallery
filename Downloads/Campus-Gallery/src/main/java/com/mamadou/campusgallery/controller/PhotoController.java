package com.mamadou.campusgallery.controller;

import com.mamadou.campusgallery.service.PhotoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/api/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> uploadPhoto(@RequestPart("title") String title,@RequestPart("file") MultipartFile file,@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        return photoService.upload(title,file,userDetails);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<?>deletePhoto(@RequestParam("id") long id,@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        return photoService.deletePhoto(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPhotoById(@RequestParam("id") long id){
        return photoService.getPhotoById(id);
    }

    @GetMapping
    public ResponseEntity<?>getAllPhotos(){
        return photoService.getAllPhotos();
    }
}
