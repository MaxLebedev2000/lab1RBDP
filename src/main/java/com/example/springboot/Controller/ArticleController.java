package com.example.springboot.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor

public class ArticleController {
    private final PostsService postsService;
    private final CommentsService commentsService;

    @GetMapping
    public Iterable<Post> getAllPosts() {

        public Post getPost ( @PathVariable int id){
            return postsService.getPostById(id);
        }

        @GetMapping("/{id}/comments")
        public Iterable<Comment> getCommentsForPost ( @PathVariable int id){
            return commentsService.getPostComments(id);
        }

        @PostMapping("/{id}/comments")
        public Comment addCommentForPost ( @PathVariable int id, @RequestBody CommentDTO commentDTO){
            return commentsService.newCommentForPost(commentDTO, id);
        }
    }
}
