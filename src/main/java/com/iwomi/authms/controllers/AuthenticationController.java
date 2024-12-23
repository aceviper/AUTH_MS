package com.iwomi.authms.controllers;

import com.iwomi.authms.core.response.GlobalResponse;
import com.iwomi.authms.domain.entities.Token;
import com.iwomi.authms.domain.entities.User;
import com.iwomi.authms.dtos.UserDto;
import com.iwomi.authms.frameworks.externals.clients.FileManagementClient;
import com.iwomi.authms.frameworks.externals.clients.NofiaClient;
import com.iwomi.authms.frameworks.externals.models.FileModel;
import com.iwomi.authms.services.authenticate.IAuthenticateService;
import com.iwomi.authms.dtos.AuthDto;
import com.iwomi.authms.core.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "${apiV1Prefix}")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticateService iAuthenticateService;
    private  final FileManagementClient fileManagementClient;
    private final NofiaClient nofiaClient;


    // Used for souscription
    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    @Operation(
            description = "User Registration or souscription",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> register(@ModelAttribute UserDto dto) {
        System.out.println("register controller "+dto);
        System.out.println("register controller "+dto.clientCode());

        User user = iAuthenticateService.signUp(dto);

        System.out.println("before calling-----------");
        ResponseEntity<List<FileModel>> response = fileManagementClient.fileUpload(
                dto.clientCode(),
                dto.files()
        );
        List<FileModel> data = response.getBody();

        // send to validation table in nofia ms
        ResponseEntity<?> nofiaResponse = nofiaClient.sendToValidation(dto.clientCode());
        System.out.println("Nofia response "+nofiaResponse);
        System.out.println("Nofia response "+nofiaResponse.getBody());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("user", user);
        responseBody.put("files", data);
        return GlobalResponse.responseBuilder("User created", HttpStatus.CREATED, HttpStatus.CREATED.value(), responseBody);
    }

    @PostMapping("/login")
    @Operation(
            description = "User Authentication",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> authenticate(@RequestBody AuthDto dto) {
        System.out.println("login controller*********");
        AuthenticationResponse response = iAuthenticateService.signIn(dto);
        System.out.println("login controller result " + response);
        return GlobalResponse.responseBuilder("User Authenticated", HttpStatus.OK, HttpStatus.OK.value(), response);
    }

    @GetMapping("/validate-token")
    @Operation(
            description = "Valid Token",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Token.class))}),
            }
    )
    public ResponseEntity<?> checkToken(@RequestParam String token) {
        Boolean response = iAuthenticateService.validateToken(token);
        return GlobalResponse.responseBuilder("Token is valid", HttpStatus.OK, HttpStatus.OK.value(), response);
    }

    @GetMapping("/token/uuid")
    @Operation(
            description = "Return uuid of user if token is valid",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Token.class))}),
            }
    )
    public ResponseEntity<?> getUserUUid(@RequestParam String token) {
        Boolean isValid = iAuthenticateService.checkTokenValidity(token);
        if(!isValid) return  ResponseEntity.status(403).body("Invalid token");

        String userId = iAuthenticateService.getUserIdFromToken(token);
        return ResponseEntity.ok(userId);
    }


    @PostMapping("/refresh-token")
    @Operation(
            description = "Refresh User Token",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        iAuthenticateService.refreshToken(request, response);
    }
}
