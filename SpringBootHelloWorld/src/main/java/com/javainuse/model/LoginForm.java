package com.javainuse.model;

//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
    
    private String email;

  //  @NotNull
    @Size(min=1, max=50)
    private String password;

    public String getemail() {
        return email;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
