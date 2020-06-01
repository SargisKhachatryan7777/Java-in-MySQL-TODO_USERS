package homework.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private int age;
    private String name;
    private String surname;
    private String phoneNumber;
    private Gender gender;
    private String email;
    private String password;

    public User(int age, String name, String surname, String phoneNumber, Gender gender, String email, String password) {
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }
}