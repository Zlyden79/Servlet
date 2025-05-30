package ru.netology.repository;

import org.junit.jupiter.api.Test;
import ru.netology.model.Post;

import java.util.Map;

public class TestPostRepository {

    @Test
    void testSave(){
        PostRepository repository = PostRepository.getInstance();
        Post post1 = new Post();
        post1.setContent("first post");
        repository.save(post1);
        System.out.println(repository.getLastId());
        Post post2 = new Post();
        post2.setContent("second post");
        repository.save(post2);
        Post post3 = new Post();
        post3.setContent("third post");
        repository.save(post3);
        Post post4 = new Post();
        post4.setContent("fourth post");
        repository.save(post4);
        System.out.println(repository.getLastId());
        System.out.println(repository.getById(3));
        System.out.println(repository.getById(1));
        System.out.println(repository.getById(8));
        System.out.println(repository.all());
        repository.removeById(2);
        System.out.println(repository.all());
        Post post5 = new Post();
        post5.setId(3);
        post5.setContent("Now it is fifth post");
        repository.save(post5);
        System.out.println(repository.all());
    }
}
