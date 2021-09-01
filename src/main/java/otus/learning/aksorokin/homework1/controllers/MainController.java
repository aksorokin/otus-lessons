package otus.learning.aksorokin.homework1.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import otus.learning.aksorokin.homework1.model.User;
import otus.learning.aksorokin.homework1.service.UserService;

import java.util.List;

@Controller
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/users/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<User> allUsers = userService.findAllUsers();
        List<User> userFriends = userService.findUserFriends(user.getUserId());
        allUsers.remove(user);
        for(User friend: userFriends){
            allUsers.remove(friend);
        }

        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("users", allUsers);
        modelAndView.addObject("userFriends", userFriends);
        modelAndView.setViewName("users/home");
        return modelAndView;
    }
    @RequestMapping(value="/users/addFriend/{id}", method = RequestMethod.POST)
    public ModelAndView addFriend(@PathVariable("id") long friendId){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        userService.addFriendById(user.getUserId(), friendId);
        List<User> allUsers = userService.findAllUsers();
        List<User> userFriends = userService.findUserFriends(user.getUserId());
        allUsers.remove(user);
        for(User friend: userFriends){
            allUsers.remove(friend);
        }
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("users", allUsers);
        modelAndView.addObject("userFriends", userFriends);
        modelAndView.setViewName("users/home");
        return modelAndView;
    }
    @RequestMapping(value="/users/deleteFriend/{id}", method = RequestMethod.POST)
    public ModelAndView deleteFriend(@PathVariable("id") long friendId){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        userService.deleteFriendById(user.getUserId(), friendId);
        List<User> allUsers = userService.findAllUsers();
        List<User> userFriends = userService.findUserFriends(user.getUserId());
        allUsers.remove(user);
        for(User friend: userFriends){
            allUsers.remove(friend);
        }
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("users", allUsers);
        modelAndView.addObject("userFriends", userFriends);
        modelAndView.setViewName("users/home");
        return modelAndView;
    }
}
