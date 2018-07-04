package com.bookonspring.booklog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController // ①
public class CommentRestController {

    @Autowired
    CommentDao commentDao;

    @RequestMapping(value = "/comments", method = RequestMethod.GET) // ②
    public List<Comment> list(@RequestParam(value = "postId", required = true) int postId) {

        return commentDao.findByPostId(postId);
    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST) // ③
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

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE) // ⑤
    @ResponseStatus(HttpStatus.NO_CONTENT) // ⑥
    public void delete(@RequestParam(value = "postId", required = true) int postId, @PathVariable int id) {

        commentDao.deleteById(id);
    }
}
