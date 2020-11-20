package tech.kristoffer.webshop.models.requests;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Size(min = 3, max= 20, message = "Användarnamn måste vara mellan 3 och 20 tecken")
    @NotBlank(message = "Användarnamn är ett krav")
    private String username;
    @NotBlank(message = "Lösenord är ett krav")
    @Size(min = 4, max= 20, message = "Lösenord måste vara mellan 4 och 20 tecken")
    private String password;
}
