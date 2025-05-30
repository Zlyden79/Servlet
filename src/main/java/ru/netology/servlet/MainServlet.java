package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String SLASH = "/";
    private static final String POSTS_PATH = "/api/posts";
    private static final String PATH_WITH_ID = "/api/posts/\\d+";

    public static long getIdFromPath(String path) throws NumberFormatException {
        long id = Long.parseLong(path.substring(path.lastIndexOf(SLASH) + 1));
        return id;
    }

    @Override
    public void init() {
        final PostRepository repository = PostRepository.getInstance();
        System.out.println(repository);
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(METHOD_GET) && path.equals(POSTS_PATH)) {
                controller.all(resp);
                return;
            }
            if (method.equals(METHOD_GET) && path.matches(PATH_WITH_ID)) {
                // easy way
                final var id = getIdFromPath(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(METHOD_POST) && path.equals(POSTS_PATH)) {
                controller.save(req.getReader(), resp);
                return;
            }

            if (method.equals(METHOD_POST) && path.matches(PATH_WITH_ID)) {
                controller.save(req.getReader(), resp);
                return;
            }

            if (method.equals(METHOD_DELETE) && path.matches(PATH_WITH_ID)) {
                // easy way
                final var id = getIdFromPath(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (NotFoundException nfe) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException nfe) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

