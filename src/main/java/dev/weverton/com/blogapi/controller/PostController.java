package dev.weverton.com.blogapi.controller;

import dev.weverton.com.blogapi.dto.PostDTO;
import dev.weverton.com.blogapi.entities.Post;
import dev.weverton.com.blogapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> index(){
        List<Post> list = postService.list();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> show(@PathVariable Long id){
        Post post = postService.get(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> store(@RequestBody PostDTO dto){
        Post postStored = postService.store(dto.toEntity());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(postStored.getId()).toUri();
        return ResponseEntity.created(uri).body(postStored);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody PostDTO dto){
        Post postUpdated = postService.update(id, dto.toEntity());
        return ResponseEntity.ok(postUpdated);
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable Long id){
        postService.destroy(id);
    }

}
