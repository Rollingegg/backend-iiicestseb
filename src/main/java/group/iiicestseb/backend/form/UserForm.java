package group.iiicestseb.backend.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    /**
     * 用户登录用户名
     */
    @Size(min = 6,max = 20,message = "用户名长度要在6至20个字符之间")
    @NotNull("用户名不能为空")
    @Pattern(message = "用户名不能含有空格",regexp = "\\S+")
    private String username;

    /**
     * 用户登录密码
     */
    @Size(min = 6,max = 20,message = "密码长度要在6至20个字符之间")
    @NotNull("密码不能为空")
    @Pattern(message = "密码不能含有空格",regexp = "\\S+")
    private String password;

}
