package task5.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import task5.spring.model.User;
//import task5.spring.service.SecurityService;
import task5.spring.service.SecurityService;
import task5.spring.service.UserService;
import task5.spring.validator.UserValidator;

@Controller
public class UserController {
    private UserService userService;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Deprecated
    //setter - deprecated
//    @Autowired
//    public void setUserService(UserService userService) {
//
//        this.userService = userService;
//    }

    //GET - page Read list of users
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String readUserList(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "read";
        //return "user";
        //  return "login_old";
    }

    //GET - page Read list of users
/*    @RequestMapping(value = "/admin/read", method = RequestMethod.GET)
    public String readUserList2(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin/read";
    }
*/

    @RequestMapping(value = "/admin/read", method = RequestMethod.GET)
    public ModelAndView readUserList3() {
        ModelAndView modelAndView = new ModelAndView();
        //передаем на JSP объект allUsers
        modelAndView.addObject("allUsers", userService.getAllUsers());
        //указываем вьюхе страницу
        modelAndView.setViewName("admin/read");
        return modelAndView;
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "create";
    }
    //GET - page Create
    @RequestMapping(value = "/admin/create", method = RequestMethod.GET)
    public String addPage(@ModelAttribute("create") User user, Model model) {
        model.addAttribute("user", new User());
        return "admin/create";
    }


    //POST - page Create - create the user
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String addUserByUser(@ModelAttribute("create") User user,
                                @RequestParam String selectedRole,
                                BindingResult bindingResult) {


        // userService.save(user);
        userService.addUserWithRoleUser(user);

        //После регистрации - логин
 //       securityService.autoLogin(user.getUsername(), user.getConfirmPassword());

        System.out.println("Controller  - Create - 2 user");
        return "redirect:/";
    }
    //POST - page Create - create the user
    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public String addUserByAdmin(@ModelAttribute("create") User user,
                                 @RequestParam String selectedRole,
                                 BindingResult bindingResult) {

        System.out.println("Controller  - Create - 1 user");
        // userService.save(user);
        userService.addUser2(user, selectedRole);
        System.out.println("Controller  - Create - 2 user");
        return "admin/read";
    }

 /*   @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User user, BindingResult bindingResult, Model model) {
        System.out.println("Controller  - Create!!!! user");
        userValidator.validate(user, bindingResult);
        System.out.println("validate  - Create!!!! user");
        if (bindingResult.hasErrors()) {
            return "create";
        }

        System.out.println("Controller  - Save!!!! user");
        userService.save(user);

        securityService.autoLogin(user.getUsername(), user.getConfirmPassword());

        return "redirect:/";
    }
*/

/////////////////////////////////////////////

    //GET - обновляем страница обновления
    @RequestMapping(value = "/admin/update/{id}", method = RequestMethod.GET)
    public ModelAndView updatePage(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/update");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    //POST - обновляем юзера
    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String updateUser(@RequestParam(name = "selectedRole") String selectedRole,
                             @ModelAttribute("user") User user, Model model) {
        userService.updateUserAndRole(user, selectedRole);
        model.addAttribute("allUsers", userService.getAllUsers());// ? а зачем - в риде должно быть
        // return "redirect:/read";
        return "admin/read";
    }

    //GET - delete page - confirmation
    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public String deleteUserPage(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/delete";
    }

    //Post- delete
    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute("user") User user, Model model) {
        userService.deleteUserById(user.getId());
        model.addAttribute("allUsers", userService.getAllUsers()); // ? а зачем - в риде должно быть
        //  return "redirect:/read";
        return "/admin/read";
    }

/*
    //создаем юзера
    @RequestMapping(value = "/add2", method = RequestMethod.POST)
//    @ResponseBody
    public ModelAndView saveUser(@ModelAttribute("add") User user,
                                 @RequestParam String[] checkboxRoles,
                                 BindingResult bindingResult) {
        System.out.println("Controller  - ADD_2 - 1.0 user");
        ModelAndView modelAndView = new ModelAndView();
        //валидируем данные с формы
        System.out.println("Controller  - ADD_2 - 2.0 user");
 //////////       userValidator.validate(user, bindingResult);
        System.out.println("Controller  - ADD_2 - 2.1 user");
        if (bindingResult.hasErrors() || (userService.findByUsername(user.getUsername()) != null)) {
            //если есть ошибки - передаем на JSP объект myError со значением
            System.out.println("Controller  - ADD_2 - 2.2 user");
            modelAndView.addObject("myError", "Username or password invalid!");
            System.out.println("Controller  - ADD_2 - 2.3 user");
            modelAndView.setViewName("/add2");
            return modelAndView;
        }
        //проверяем роль. См. JSP - передаю hidden
        System.out.println("Controller  - ADD_2 - 3.0 user");
  /*      if (checkboxRoles.length < 2) {
            modelAndView.addObject("myError", "Role invalid. Choose a role!");
            modelAndView.setViewName("/add2");
            return modelAndView;
        }*/
/*        System.out.println("Controller  - ADD_2 - 4.0 user");
        modelAndView.setViewName("redirect:/");
        System.out.println("Controller  - ADD_2 - 5.0 user");
        this.userService.addUser2(user, checkboxRoles);
        System.out.println("Controller  - ADD_2 - 6.0  ПОСЛЕ!!! 1 user");
        return modelAndView;
    }
*/

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }














    //обработка /edit
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = this.userService.getUserById(id);
        //пустая строка для нового пароля
        String newPassword = "";
        modelAndView.setViewName("edit");
        //для метода ПОСТ добавляем во вьюху нашего user
        modelAndView.addObject("user", user);
        //передаем пустую строку для заполнения
        //   modelAndView.addObject("newPassword", newPassword);
        //передаем роли в виде String
        modelAndView.addObject("editRoles", user.getRoles().toString());
        return modelAndView;
    }
/*

    //обновляем юзера
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editUser(@RequestParam(name = "checkboxRoles") String selectedRole,
                               //  @RequestParam(name = "newPassword") String newPassword,
                                 @ModelAttribute("user") User user,
                                 BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        //проверка данных с формы
 */
/*       userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors() || (userService.findByUsername(user.getUsername()) != null)) {
            modelAndView.addObject("myError", "Username or password invalid.");
            modelAndView.setViewName("/edit");
            return modelAndView;
        }*//*

        //проверяем роль. См. JSP - передаю hidden
   */
/*     if (checkboxRoles.length < 2) {
            modelAndView.addObject("myError", "Role invalid. Choose a role!");
            modelAndView.setViewName("/add2");
            return modelAndView;
        }*//*

        //если пароль не указан - оставляем прежний
        this.userService.updateUserAndRole(user, selectedRole);
        modelAndView.setViewName("redirect:/read");
        return modelAndView;
    }

*/






    @RequestMapping(value = "/user/user", method = RequestMethod.GET)
    public String userPage(Model model) {

        return "user/user";
    }



}




