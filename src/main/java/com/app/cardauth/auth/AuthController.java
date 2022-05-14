package com.app.cardauth.auth;

import com.app.cardauth.auth.exceptions.FieldValidationFaild;
import com.app.cardauth.auth.payload.AuthRequestDto;
import com.app.cardauth.auth.payload.AuthRespondDto;
import com.app.cardauth.auth.payload.RegisterRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<AuthRespondDto> login(@RequestBody AuthRequestDto authRequestDto) throws BadCredentialsException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDto.getUserName(),
                            authRequestDto.getPassword()
                    )
            );
        }catch (BadCredentialsException e){
            //TODO add proper exception
            throw new BadCredentialsException("INVALID_USERNAME_OR_PASSWORD" , e);
        }


        return new ResponseEntity<>(userService.loginUser(authRequestDto) , HttpStatus.ACCEPTED);
    }


    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterRequestDto request){


        return new ResponseEntity<>(userService.registerUser(request) , HttpStatus.CREATED);

    }

    @GetMapping("/test")
    public String testAuth(){
        return "Auth is ok";
    }

}
