package homework.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private int id;
    private String name;
    private Date createDate;
    private Date deadline;
    private Status status;
    private int userId;

    public Todo(String name, Date createDate, Date deadline, Status status) {
        this.name = name;
        this.createDate = createDate;
        this.deadline = deadline;
        this.status = status;
    }
}