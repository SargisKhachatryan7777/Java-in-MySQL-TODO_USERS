package homework.todo.manager;

import homework.todo.db.DBConnectionProvider;
import homework.todo.model.Status;
import homework.todo.model.Todo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TodoManager {
    private Connection connection;

    public TodoManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public List<Todo> getAllTodo() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo");
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreateDate(resultSet.getDate("createDate"));
            todo.setDeadline(resultSet.getDate("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserId(resultSet.getInt("user_id"));
            todos.add(todo);
        }
        return todos;
    }

    public void addTodo(Todo todo) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into todo(name,deadline,status,user_id) Values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, todo.getName());
        preparedStatement.setDate(2, todo.getDeadline());
        preparedStatement.setString(3, String.valueOf(todo.getStatus()));
        preparedStatement.setInt(4, todo.getUserId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            todo.setId(id);
        }
    }

    public List<Todo> progres() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE STATUS='IN_PROGRESS'");
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreateDate(resultSet.getDate("createDate"));
            todo.setDeadline(resultSet.getDate("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserId(resultSet.getInt("user_id"));
            todos.add(todo);
        }
        return todos;
    }

    public void deleteTodo(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todo WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Todo> finish() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE STATUS='FINISHED'");
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreateDate(resultSet.getDate("createDate"));
            todo.setDeadline(resultSet.getDate("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserId(resultSet.getInt("user_id"));
            todos.add(todo);
        }
        return todos;
    }

    public void update(int id,String status) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE todo SET status = ? WHERE id =" + id);
        preparedStatement.setString(1, status);
        preparedStatement.executeUpdate();
        }
    }