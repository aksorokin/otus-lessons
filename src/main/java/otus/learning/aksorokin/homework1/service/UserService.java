package otus.learning.aksorokin.homework1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import otus.learning.aksorokin.homework1.model.User;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JdbcTemplate jdbcTemplate;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, JdbcTemplate jdbcTemplate) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findUserByUserName(String username) {
        List<User> users = jdbcTemplate.query(
//                "SELECT id, username, lastname, age, gender, interests, city, password, enabled  FROM education.users where username = ?",
                "SELECT id, username, lastname, age, gender, interests, city, password, enabled  FROM d3c6gmvm8i49do.users where username = ?",
                new Object[]{username},
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("lastname"),
                        rs.getInt("age"),
                        rs.getInt("gender"),
                        rs.getString("interests"),
                        rs.getString("city"),
                        rs.getString("password"),
                        rs.getBoolean("enabled")
                ));
//        ).forEach(customer -> log.info(customer.toString()));
        return users.isEmpty()  ? null: users.get(0);
    }
    public List<User> findAllUsers() {
        List<User> users = jdbcTemplate.query(
                "SELECT id, username, lastname, age, gender, interests, city, password, enabled  FROM d3c6gmvm8i49do.users",
//                "SELECT id, username, lastname, age, gender, interests, city, password, enabled  FROM education.users",
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("lastname"),
                        rs.getInt("age"),
                        rs.getInt("gender"),
                        rs.getString("interests"),
                        rs.getString("city"),
                        rs.getString("password"),
                        rs.getBoolean("enabled")
                ));
//        ).forEach(customer -> log.info(customer.toString()));
        return users;
    }

    public int saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        int result = jdbcTemplate.update(
                "INSERT INTO d3c6gmvm8i49do.users (username, lastname, age, gender, interests, city, password, enabled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
//                "INSERT INTO education.users (username, lastname, age, gender, interests, city, password, enabled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getLastName(),
                user.getAge(),
                user.getGender(),
                user.getInterests(),
                user.getCity(),
                user.getPassword(),
                user.isEnabled()
        );
        return result;
    }
    public int addFriendById(long userId, long friendId){
        int result = jdbcTemplate.update(
                "INSERT INTO d3c6gmvm8i49do.user_friends (user_id, friend_id) VALUES (?, ?)",
//                "INSERT INTO education.user_friends (user_id, friend_id) VALUES (?, ?)",
                userId,
                friendId
        );
        return result;
    }

    public List<User> findUserFriends(long userId) {
        List<User> users = jdbcTemplate.query(
                "select id, username, lastname, age, gender, interests, city, password, enabled from users inner join user_friends\n" +
                        "                       on user_friends.friend_id=users.id\n" +
                        "where user_friends.user_id=?\n",
                new Object[]{userId},
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("lastname"),
                        rs.getInt("age"),
                        rs.getInt("gender"),
                        rs.getString("interests"),
                        rs.getString("city"),
                        rs.getString("password"),
                        rs.getBoolean("enabled")
                ));
//        ).forEach(customer -> log.info(customer.toString()));
        return users;
    }

    public int deleteFriendById(long userId, long friendId) {
        int result = jdbcTemplate.update(
                "DELETE FROM d3c6gmvm8i49do.user_friends WHERE user_id=? and friend_id=? ",
//                "DELETE FROM education.user_friends WHERE user_id=? and friend_id=? ",
                userId,
                friendId
        );
        return result;
    }
}
