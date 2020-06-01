package homework.todo.manager;


import homework.todo.db.DBConnectionProvider;
import homework.todo.model.Gender;
import homework.todo.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {

    private Connection connection;

    public UserManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }



    public void add(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user(age,name,surname,gender,phoneNumber,email,password) Values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, user.getAge());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getSurname());
        preparedStatement.setString(4, String.valueOf(user.getGender()));
        preparedStatement.setString(5, user.getPhoneNumber());
        preparedStatement.setString(6, user.getEmail());
        preparedStatement.setString(7, user.getPassword());

        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setAge(resultSet.getInt("age"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setGender(Gender.valueOf(resultSet.getString("gender")));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            users.add(user);
        }
        return users;
    }
}