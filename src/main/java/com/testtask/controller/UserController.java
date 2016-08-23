package com.testtask.controller;

import com.testtask.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import com.testtask.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String allUsers(@RequestParam(value="page", required = false) Long page, Model model) {
        if (null == page)
            page = 1L;
        model.addAttribute("user", new User());
        model.addAttribute("searchedUser", new User());
        model.addAttribute("allUsers", userService.getUsers(page));
        model.addAttribute("page", page);
        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user) {
        if (user.getId() == 0) {
            userService.createUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "update/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestParam(value="page", required = false) Long page, Model model) {
        if (null == page)
            page = 1L;
        model.addAttribute("user", userService.readUser(id));
        model.addAttribute("searchedUser", new User());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("page", page);
        return "users";
    }

    @RequestMapping(value = "userdetails/{id}")
    public String userDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.readUser(id));
        return "userdetails";
    }
    
    @RequestMapping(value="searchresults", method = RequestMethod.POST)
    public String searchResults(@ModelAttribute("searchedUser") User user, Model model) {
        List<User> searchResult = userService.getUsersByName(user.getName());
        model.addAttribute("listUsers", searchResult);

        return "searchresults";
    }
}
