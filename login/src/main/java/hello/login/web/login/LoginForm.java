package hello.login.web.login;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

// DTO
@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
