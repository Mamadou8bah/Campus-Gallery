package com.mamadou.campusgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public String uploadFile(MultipartFile file) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap());

        Object secureUrl = result.get("secure_url");
        if (secureUrl != null) {
            return secureUrl.toString();
        }


        Object url = result.get("url");
        return url != null ? url.toString() : null;
    }


    public boolean deleteFile(String publicId) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) cloudinary.uploader()
                .destroy(publicId, ObjectUtils.emptyMap());

        Object res = result.get("result");
        return "ok".equals(res) || "deleted".equals(res);
    }
}
