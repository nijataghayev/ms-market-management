package com.example.marketmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: nijataghayev
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UserProfileReqDto profileDto;
    private UserCredentialsDto credentialsDto;
}
