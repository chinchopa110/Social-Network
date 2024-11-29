package com.example.webapp1.Application;

import com.example.webapp1.Diaries.UserDiary;
import com.example.webapp1.Repos.IUserData;
import com.example.webapp1.Diaries.Posts.UserPost;
import com.example.webapp1.Users.MyProfile;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.webapp1.Users.Domain.User;
import org.springframework.web.bind.annotation.*;

@Controller
public class InitApplication implements IApplication {

    @Autowired
    private IUserData _userData;

    private User findUserById(long userId) {
        for (User user : _userData.findAll()) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/initapp")
    public String Show(Model model, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");

        Iterable<User> users = _userData.findAll();
        model.addAttribute("users", users);
        model.addAttribute("myProfile", myProfile);
        return "homePage";
    }

    @PostMapping("/addPost")
    public String addPost(@RequestParam String title, @RequestParam String message, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");

        if (myProfile != null) {
            UserPost newPost = new UserPost(title, message);
            myProfile.Diary.addPost(newPost);

            User user = findUserById(myProfile.Id);
            if (user != null) {
                user.getDiary().addPost(newPost);
                _userData.save(user);
            }

            return "redirect:/yourDiary";
        } else {
            return "redirect:/loginForm";
        }
    }

    @PostMapping("/removePost")
    public String removePost(@RequestParam int postIndex, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");

        if (myProfile != null) {
            User user = findUserById(myProfile.Id);
            if (user != null) {
                if (postIndex >= 0 && postIndex < myProfile.Diary.getPosts().size() && postIndex < user.getDiary().getPosts().size()) {
                    myProfile.Diary.removePost(postIndex);
                    user.getDiary().removePost(postIndex);
                    _userData.save(user);
                }
                return "redirect:/yourDiary";
            }
        }
        return "redirect:/loginForm";
    }

    @GetMapping("/yourDiary")
    public String ShowMyDiary(Model model, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");

        if (myProfile != null) {
            model.addAttribute("diary", myProfile.Diary.getPosts());
            model.addAttribute("userName", myProfile.Name);
            model.addAttribute("MyId", myProfile.Id);
            return "yourDiary";
        } else {
            model.addAttribute("error", "Пожалуйста, войдите в систему.");
            return "loginForm";
        }
    }

    @GetMapping("init/user/{id}")
    public String ShowUserDiary(@PathVariable int id, Model model) {
        User user = findUserById(id);

        if (user != null) {
            model.addAttribute("diary", user.getDiary().getPosts());
            model.addAttribute("userName", user.getName());
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getId());
        } else {
            model.addAttribute("error", "Пользователь не найден.");
        }
        return "userDiaryInit";
    }

    @PostMapping("/likePost")
    public String likePost(@RequestParam Long postId, @RequestParam Long userId) {
        User user = findUserById(userId);
        if (user != null) {
            user.getDiary().likeDiaryPost(postId, user);
            _userData.save(user);
            return "redirect:/init/user/" + user.getId();
        } else {
            return "redirect:/homePage";
        }
    }

    @PostMapping("/unlikePost")
    public String unlikePost(@RequestParam Long postId, @RequestParam Long userId) {
        User user = findUserById(userId);
        if (user != null) {
            user.getDiary().unlikeDiaryPost(postId, user);
            _userData.save(user);
            return "redirect:/init/user/" + user.getId();
        } else {
            return "redirect:/homePage";
        }
    }

    @GetMapping("init/user/{id}/comm/{postId}")
    public String showUserComment(@PathVariable int id, @PathVariable long postId, Model model) {
        User user = findUserById(id);

        if (user != null) {
            UserDiary userDiary = user.getDiary();
            UserPost post = userDiary.findPostById(postId);

            if (post != null) {
                model.addAttribute("comments", post.getComments());
                model.addAttribute("user", user);
                model.addAttribute("postId", postId);
            } else {
                model.addAttribute("error", "Пост не найден.");
            }
        } else {
            model.addAttribute("error", "Пользователь не найден.");
        }

        return "userCommentInit";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam Long userId, @RequestParam Long postId, @RequestParam String message, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");

        if (myProfile != null) {
            User user = findUserById(userId);
            if (user != null) {
                UserDiary userDiary = user.getDiary();
                UserPost post = userDiary.findPostById(postId);

                if (post != null) {
                    User tmp = findUserById(myProfile.Id);
                    post.addComment(tmp, message);
                    _userData.save(user);
                }
            }
            return "redirect:/init/user/" + userId + "/comm/" + postId;
        } else {
            return "redirect:/loginForm";
        }
    }


    @PostMapping("/likeComment")
    public String likeComment(@RequestParam Long commId, @RequestParam Long userId, @RequestParam Long postId, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");
        User user = findUserById(userId);
        if (user != null) {
            UserPost post = user.getDiary().findPostById(postId);
            if (post != null) {
                User tmp = findUserById(myProfile.Id);
                post.LikeComment(commId, tmp);
                _userData.save(user);
                return "redirect:/init/user/" + userId + "/comm/" + postId;
            }
        }
        return "redirect:/homePage";
    }

    @PostMapping("/unlikeComment")
    public String unlikeComment(@RequestParam Long commId, @RequestParam Long userId, @RequestParam Long postId, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");
        User user = findUserById(userId);
        if (user != null) {
            UserPost post = user.getDiary().findPostById(postId);
            if (post != null) {
                User tmp = findUserById(myProfile.Id);
                post.UnlikeComment(commId, tmp);
                _userData.save(user);
                return "redirect:/init/user/" + userId + "/comm/" + postId;
            }
        }
        return "redirect:/homePage";
    }

}
