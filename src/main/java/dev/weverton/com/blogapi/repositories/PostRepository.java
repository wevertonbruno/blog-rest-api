package dev.weverton.com.blogapi.repositories;

import dev.weverton.com.blogapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Optional<Post> findBySlug(@Param("slug") String slug);

    @Query("SELECT obj FROM Post obj WHERE obj.slug = :slug AND id != :id")
    public Optional<Post> findBySlug(@Param("slug") String slug, @Param("id") Long id);
}
