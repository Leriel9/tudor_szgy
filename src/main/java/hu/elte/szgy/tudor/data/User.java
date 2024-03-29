package hu.elte.szgy.tudor.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String username;
    private String password;

    public enum UserType {
	UGYFEL, TUDOR, ADMIN
    }

    @Enumerated(EnumType.STRING)
    private UserType type;
    private int userId;

    public String getUsername() { return this.username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

    public UserType getType() { return this.type; }

    public void setType(UserType type) { this.type = type; }

    public int getUserId() { return this.userId; }

    public void setUserid(int userId) { this.userId = userId; }

}
