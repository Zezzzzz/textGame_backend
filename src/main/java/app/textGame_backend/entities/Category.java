package app.textGame_backend.entities;

import javax.persistence.*;

@Entity
@Table
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int categoryID;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="thread_id")
    private int threadID;

    public Category() {
    }

    public Category(int categoryID, String name, String description, int threadID) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
        this.threadID = threadID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }
}
