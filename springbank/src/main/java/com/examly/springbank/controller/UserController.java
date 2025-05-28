package com.examly.springbank.controller;
@RestController
@CrossOrigin(origins="*")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public user registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }
}
