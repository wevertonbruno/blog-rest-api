package dev.weverton.com.blogapi.dto;

import com.sun.istack.NotNull;
import dev.weverton.com.blogapi.entities.Post;
import dev.weverton.com.blogapi.entities.Tag;
import dev.weverton.com.blogapi.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    @NotNull
    private String title;

    @NotNull
    private String slug;

    @NotNull
    private String body;

    private List<Tag> tags = new ArrayList<Tag>();

    public Post toEntity(){
        return new Post(title, slug, body, tags);
    }
}
