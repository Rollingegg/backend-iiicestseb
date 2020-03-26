package group.iiicestseb.backend.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户登录信息表单
 * @author wph
 * @date 2020/03/01
 */
@Data
@NoArgsConstructor
public class UserForm {

    public static final String USERNAME_EMPTY = "用户名不能为空";
    public static final String PASSWORD_EMPTY = "密码不能为空";
    public static final String USERNAME_LENGTH_INVALID = "用户名长度要在6至20个字符之间";
    public static final String PASSWORD_LENGTH_INVALID = "密码长度要在6至20个字符之间";
    public static final String USERNAME_CONTAIN_SPACE ="用户名不能含有空格";
    public static final String PASSWORD_CONTAIN_SPACE ="密码不能含有空格";

    /**
     * 用户登录用户名
     */
    @Size(min = 6,max = 20,message = USERNAME_LENGTH_INVALID)
    @NotNull(USERNAME_EMPTY)
    @Pattern(message = USERNAME_CONTAIN_SPACE,regexp = "\\S+")
    private String username;

    /**
     * 用户登录密码
     */
    @Size(min = 6,max = 20,message = PASSWORD_LENGTH_INVALID)
    @NotNull("密码不能为空")
    @Pattern(message = PASSWORD_CONTAIN_SPACE,regexp = "\\S+")
    private String password;

}
