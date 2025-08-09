package com.mamadou.campusgallery.repository;

import com.mamadou.campusgallery.model.Photo;
import com.mamadou.campusgallery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findByUser(User user);
}
