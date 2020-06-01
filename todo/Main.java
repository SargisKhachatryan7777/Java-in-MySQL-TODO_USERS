package homework.todo;

import homework.todo.manager.TodoManager;
import homework.todo.manager.UserManager;
import homework.todo.model.Gender;
import homework.todo.model.Status;
import homework.todo.model.Todo;
import homework.todo.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main implements Commands {
    public static Scanner scanner = new Scanner(System.in);
    private static User currentUser;
    private static UserManager userManager = new UserManager();
    private static TodoManager todoManager = new TodoManager();

    public static void main(String[] args) throws SQLException {

        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;

                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void loginUser() throws SQLException {
        System.out.println("Please input email,password");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArr = loginStr.split(",");
            List<User> allUsers = userManager.getAllUsers();
            int k = 0;
            for (User user : allUsers) {
                if (loginArr[0] != null && user.getPassword().equals(loginArr[1])) {
                    currentUser = user;
                    k++;
                    loginSuccess();
                }
            }
            if (k == 0) {
                System.out.println("Wrong phoneNumber or password");
            }

        } catch (
                ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data!");
        }
    }


    private static void loginSuccess() throws SQLException {
        System.out.println("Welcome " + currentUser.getName() + "!");
        boolean isRun = true;
        while (isRun) {
            Commands.printCommandsTodo();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case MY_LIST:
                    myList();
                    break;
                case MY_IN_PROGRESS_LIST:
                    inProgress();
                    break;
                case MY_FINISHED_LIST:
                    finished();
                    break;
                case ADD_TODO:
                    addTodo();
                    break;
                case CHANGE_TODO_STATUS:
                    updateTodo();
                    break;
                case DELETE_TODO_BY_ID:
                    deleteTodoById();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void registerUser() {
        System.out.println("Please input user data " +
                "age,name,surname,gender(MALE,FEMALE),phoneNumber,email,password");
        try {
            String userDataStr = scanner.nextLine();
            String[] userDataArr = userDataStr.split(",");
            if (userDataStr != null) {
                User user = new User();
                user.setAge(Integer.parseInt(userDataArr[0]));
                user.setName(userDataArr[1]);
                user.setSurname(userDataArr[2]);
                user.setGender(Gender.valueOf(userDataArr[3]));
                user.setPhoneNumber(userDataArr[4]);
                user.setEmail(userDataArr[5]);
                user.setPassword(userDataArr[6]);
                try {
                    userManager.add(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("User was successfully added");
            } else {
                System.out.println("User already exists!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data!");
        }
    }

    private static void addTodo() {
        System.out.println("Please input todo data " +
                "name,deadline,status(IN_PROGRESS OR FINISHED)");
        try {
            String userDataStr = scanner.nextLine();
            String[] userDataArr = userDataStr.split(",");
            if (userDataStr != null) {
                Todo todo = new Todo();
                todo.setName(userDataArr[0]);
                todo.setDeadline(Date.valueOf(userDataArr[1]));
                todo.setStatus(Status.valueOf(userDataArr[2]));
                todo.setUserId(currentUser.getId());
                try {
                    todoManager.addTodo(todo);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("Todo was successfully added");
            } else {
                System.out.println("Todo already exists!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data!");
        }
    }

    public static void finished() throws SQLException {
        List<Todo> allTodo = todoManager.finish();
        for (Todo todo : allTodo) {
            if (currentUser.getId() == todo.getUserId()) {
                System.out.println(todo);
            }
        }
    }

    public static void inProgress() throws SQLException {
        List<Todo> allTodo = todoManager.progres();
        for (Todo todo : allTodo) {
            if (currentUser.getId() == todo.getUserId()) {
                System.out.println(todo);
            }
        }
    }

    public static void deleteTodoById() throws SQLException {

        List<Todo> allTodo = todoManager.getAllTodo();
        for (Todo todo : allTodo) {
            if (currentUser.getId() == todo.getUserId()) {
                System.out.println(todo);
            }
        }
        System.out.println("Please input todo Id");
        int id = Integer.parseInt(scanner.nextLine());
        todoManager.deleteTodo(id);
        System.out.println("ok");
    }

    public static void myList() throws SQLException {
        List<Todo> allTodo = todoManager.getAllTodo();
        for (Todo todo : allTodo) {
            if (currentUser.getId() == todo.getUserId()) {
                System.out.println(todo);
            }
        }
    }

    public static void updateTodo() throws SQLException {
        List<Todo> allTodo = todoManager.getAllTodo();
        for (Todo todo : allTodo) {
            if (currentUser.getId() == todo.getUserId()) {
                System.out.println(todo);
            }
        }
        System.out.println("please input id");
        int id=Integer.parseInt(scanner.nextLine());
        System.out.println("please input status(IN_PROGRESS OR FINISHED)");
        String status=scanner.nextLine();
        todoManager.update(id,status);
    }
}