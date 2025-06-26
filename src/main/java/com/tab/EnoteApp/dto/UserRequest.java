package com.tab.EnoteApp.dto;
import lombok.*;
import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobNo;
    private List<RoleDto> roles;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleDto{
        private Integer id;
        private String name;
    }
}
