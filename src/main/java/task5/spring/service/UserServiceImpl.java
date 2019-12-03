package task5.spring.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task5.spring.dao.RoleDao;
import task5.spring.model.Role;
import task5.spring.model.User;
import task5.spring.dao.UserDao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    //  @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;





//    @Autowired
//    public UserServiceImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }

/*
    @Autowired
    public UserServiceImpl(UserDao userDao , RoleDao roleDao   , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
//      //  this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
*/

    @Deprecated
    //setter - deprecated
    //  @Autowired
    //  public void setUserDao(UserDao userDao) {
    //       this.userDao = userDao;
    //   }


    //1-й получить список всех пользователей
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return this.userDao.getAllUsersDao();
    }



    //2-й получить пользователя по ID
    @Override
    @Transactional
    public User getUserById(long id) /*throws SQLException*/ {
        return userDao.getUserById(id);
    }

    //3-й проверить есть ли зарегистрированный пользователь с искомым именем
    @Override
    @Transactional
    public boolean checkUserByName(String name) throws SQLException {
        return false;
    }

    //4-й проверить есть ли зарегистрированный пользователь с искомым логином
    @Override
    @Transactional
    public boolean checkUserByLogin(String login) throws SQLException {
        return false;
    }

    //5-й создать и добавить в базу нового пользователя
    @Override
    @Transactional
    public void addUser(User user) {
        System.out.println("userservise - add user");
        userDao.addUser(user);
    }

    //6-й обновить и записать в базу новые данные пользователя
    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }


    @Override
    public void updateUserAndRole(User user, String selectedRole) {
        //если новый пароль от 3 до 5 символов - берем его
        //       if (editPassword.length() > 2 && editPassword.length() < 6) {
        //           user.setPassword(passwordEncoder.encode(editPassword));
        //       }
        //  user.setPassword(editPassword);
        Set<Role> roles = new HashSet<>();
        //проверка роли из введённой в форму
        inputRole(roles, selectedRole);
        user.setRoles(roles);
        userDao.updateUser(user);
    }


    //7-й удалить пользователя через ID
    @Override
    @Transactional
    public void deleteUserById(long id) /*throws SQLException*/ {
        userDao.deleteUserByIdDao(id);

    }

    //8-й проверить есть ли зарегистрированный пользователь
    // с искомым login и password
    @Override
    @Transactional
    public User isExist(String login, String password) {
        return null;
    }

    // метод добавления юзера по умолчанию - права ЮЗЕР
    @Override
    public void addUserWithRoleUser(User user) {

        System.out.println("userservice - SAVE!!!! user");
        //      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(1L)); //роль - 1 - Юзер по умолчанию
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        System.out.println("return userDao.findByUsername(username);");
        return userDao.findByUsername(username);

    }


    // метод добавления юзера Админом  - права ЮЗЕР или АДМИН
    @Override
    public void addUser2(User user, String selectedRole) {
        //    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        // //проверка роли из введённой в форму
        inputRole(roles, selectedRole);

        user.setRoles(roles);

        userDao.addUser(user);
    }


    public void inputRole(Set<Role> roles, String selectedRole) {

        if (selectedRole.equals("ROLE_ADMIN")) {
            roles.add(roleDao.getOne(2L));
        }
        if (selectedRole.equals("ROLE_USER")) {
            roles.add(roleDao.getOne(1L));
        }
    }





}