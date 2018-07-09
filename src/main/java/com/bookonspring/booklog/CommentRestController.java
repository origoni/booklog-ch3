package com.bookonspring.booklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comments") // ①
public class CommentRestController {

    @Autowired
    CommentDao commentDao;

    @GetMapping // @RequestMapping(value = "/comments", method = RequestMethod.GET) // ②
    public List<Comment> list(@RequestParam(value = "postId", required = true) int postId) {

        return commentDao.findByPostId(postId);
    }

    @PostMapping // @RequestMapping(value = "/comments", method = RequestMethod.POST) // ③
    @ResponseStatus(HttpStatus.CREATED) // ④
    public Comment save(@RequestParam(value = "postId", required = true) int postId,
                        @RequestParam(value = "content", required = true) String content,
                        @RequestParam(value = "userName", required = true) String userName) {

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setContent(content);
        comment.setUserName(userName);
        comment.setCreatedAt(new Date());

        return commentDao.save(comment);
    }

    @DeleteMapping("/{id}") // @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE) // ⑤
    @ResponseStatus(HttpStatus.NO_CONTENT) // ⑥
    public void delete(@RequestParam(value = "postId", required = true) int postId, @PathVariable int id) {

        commentDao.deleteById(id);
    }
}
