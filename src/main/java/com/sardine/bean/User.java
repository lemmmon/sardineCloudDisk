package com.sardine.bean;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String password;
    private String token;
    private String mac;
    private String userRole;
    private String validTokenTime;
}
