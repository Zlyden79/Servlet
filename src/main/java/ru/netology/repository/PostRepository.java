package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Singletone
public class PostRepository {
    public volatile static PostRepository instance;
    // храним в мапе для быстрого поиска, key = Post.getId();
    private final Map<Long, Post> repo;
    //сюда запоминаем последний id для постов
    private volatile AtomicLong lastId;


    private PostRepository() {
        this.repo = new ConcurrentHashMap<>();
        this.lastId = new AtomicLong();
        lastId.set(0l);
    }

    public static PostRepository getInstance() {
        if (instance == null) {
            synchronized (PostRepository.class) {
                if (instance == null) {
                    instance = new PostRepository();
                }
            }
        }
        return instance;
    }

    public List<Post> all() {
        if (repo.values().size() == 0) return Collections.emptyList();
        return new ArrayList<>(repo.values());
    }

    public Optional<Post> getById(long id) {
        Optional<Post> result = Optional.ofNullable(repo.get(id));
        return result;
    }

    public Post save(Post post) {
        long id = (post.getId() == 0) ? lastId.incrementAndGet() : post.getId();
        post.setId(id);
        repo.put(id, post);
        return post;
    }

    public void removeById(long id) {
        if (getById(id).isEmpty()) {
            throw new NotFoundException();
        }
        repo.remove(id);
    }

    public Map<Long, Post> getRepo() {
        return repo;
    }

    public AtomicLong getLastId() {
        return lastId;
    }
}
