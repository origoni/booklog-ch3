package com.bookonspring.booklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostDao postDao;

    @ModelAttribute
    public Post formPost() {
        return new Post();
    }

    @GetMapping(value = "/write")
    public String form() {
        return "blog/form";
    }

    @PostMapping(value = "/write")
    public String write(@Valid Post post, Errors errors) {
        if (errors.hasErrors()) return "form";
        post.setCreatedAt(new Date());
        return "redirect:/post/" + postDao.save(post).getId();
    }

    @RequestMapping("/{id}")
    public String view(Model model, @PathVariable int id) {
        Post post = postDao.getOne(id);
        model.addAttribute("post", post);
        return "blog/post";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<Post> postList = postDao.findAll();
        model.addAttribute("postList", postList);
        return "blog/list";
    }

    @GetMapping(value = "/{id}/edit") // ①
    public String editor(Model model, @PathVariable int id) {
        Post post = postDao.getOne(id);
        model.addAttribute("post", post);
        return "blog/form"; // ③
    }

    @PostMapping(value = "/{id}/edit") // ②
    public String edit(Post post) {
        return "redirect:/post/" + postDao.save(post).getId();
    }

    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        postDao.deleteById(id);
        return "redirect:/post/list";
    }
}
