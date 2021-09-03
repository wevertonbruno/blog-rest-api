package dev.weverton.com.blogapi.services;

import dev.weverton.com.blogapi.entities.Post;
import dev.weverton.com.blogapi.entities.Tag;
import dev.weverton.com.blogapi.exceptions.DuplicatedKeyException;
import dev.weverton.com.blogapi.exceptions.EntityNotFoundException;
import dev.weverton.com.blogapi.repositories.PostRepository;
import dev.weverton.com.blogapi.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private TagRepository tagRepository;

    @Autowired
    public PostService(PostRepository postRepository, TagRepository tagRepository){
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public Post store(Post postData){
        Optional<Post> postBySlug = postRepository.findBySlug(postData.getSlug());
        if(postBySlug.isPresent()){
            throw new DuplicatedKeyException("Slug '" + postData.getSlug() + "' already exist.");
        }

        Post post = new Post();

        post.setTitle(postData.getTitle());
        post.setSlug(postData.getSlug());
        post.setBody(postData.getBody());

        post.setTags(createTags(postData.getTags()));

        return postRepository.save(post);
    }

    private List<Tag> createTags(List<Tag> tagsData){
        List<Tag> tags = new ArrayList<Tag>();
        for (Tag tag : tagsData){
            Optional<Tag> findTag = tagRepository.findByDescription(tag.getDescription());
            if(findTag.isPresent())
                tags.add(findTag.get());
            else
                tags.add(tagRepository.save(tag));
        }

        return tags;
    }

    public List<Post> list(){
        return postRepository.findAll();
    }

    public Post get(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with id(" + id + ") doesn't exist."));
    }

    public Post update(Long id, Post postData){
        if(postRepository.findBySlug(postData.getSlug(), id).isPresent()){
            throw new DuplicatedKeyException("Post with slug '" + id + "' already exists.");
        }

        return postRepository.findById(id).map( post -> {
            post.setTitle(postData.getTitle());
            post.setSlug(postData.getSlug());
            post.setBody(postData.getBody());
            post.setTags(createTags(postData.getTags()));

            return postRepository.save(post);
        }).orElseThrow(() -> new EntityNotFoundException("Post with id(" + id + ") doesn't exist."));
    }

    public void destroy(Long id){
        postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with id(" + id + ") doesn't exist."));
        postRepository.deleteById(id);
    }
}
