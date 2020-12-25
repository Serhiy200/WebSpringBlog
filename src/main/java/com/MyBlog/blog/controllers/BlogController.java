package com.MyBlog.blog.controllers;

import com.MyBlog.blog.models.Post;
import com.MyBlog.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model){

        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {

        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
       return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogInfo(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
       Optional<Post> post =  postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res :: add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/update")
    public String blogUpdate(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post =  postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res :: add);
        model.addAttribute("post", res );
        //model.addAttribute("id", id);

        return "blog-update";
    }

    @PostMapping("/blog/{id}/update")
    public  String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setAnons(anons);
        post.setTitle(title);
        post.setFull_text(full_text);
        postRepository.save(post);
        /*if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post =  postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res :: add);
        model.addAttribute("post", res );
        model.addAttribute("id", id);*/
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remote")
    public String blogPostRemote(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
