package com.mamadou.campusgallery.service;

import com.mamadou.campusgallery.model.Photo;
import com.mamadou.campusgallery.model.User;
import com.mamadou.campusgallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private CloudinaryService  cloudinaryService;
    @Autowired
    private MyUserDetailsService userDetailsService;

    public ResponseEntity<?> upload(String title, MultipartFile file, UserDetails userDetails) {
        try {
            String imageUrl = cloudinaryService.uploadFile(file);

            Photo photo = new Photo();
            photo.setTitle(title);
            photo.setImageUrl(imageUrl);

            User user = (User) userDetailsService.loadUserByUsername(userDetails.getUsername());
            photo.setUser(user);

            photoRepository.save(photo);

            return ResponseEntity.ok("Photo uploaded successfully");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Upload failed: " + ex.getMessage());
        }
    }


    public ResponseEntity<?> deletePhoto(long id) {
        try {
            return photoRepository.findById(id)
                    .map(photo -> {
                        photoRepository.delete(photo);
                        String url=photo.getImageUrl();
                        return ResponseEntity.ok("Photo deleted successfully");
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Photo not found"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Delete failed: " + ex.getMessage());
        }
    }

    public ResponseEntity<?>getPhotoById(long id) {
       try{
           Photo photo=photoRepository.findById(id).orElseThrow(()->new RuntimeException("Photo not found"));
           return ResponseEntity.ok(photo);
       }catch (Exception ex){
           return ResponseEntity.badRequest().body("Could not fetch Photo with Specified Id:  " + ex.getMessage());
       }
    }

    public ResponseEntity<?>getAllPhotos() {
        try {
            List<Photo>photos=photoRepository.findAll();
            return ResponseEntity.ok(photos);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body("Fetch failed: " + ex.getMessage());
        }
    }
}
