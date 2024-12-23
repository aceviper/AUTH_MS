package com.iwomi.authms.controllers;

import com.iwomi.authms.core.enums.StatusEnum;
import com.iwomi.authms.core.enums.UserTypeEnum;
import com.iwomi.authms.core.response.GlobalResponse;
import com.iwomi.authms.domain.entities.User;
import com.iwomi.authms.dtos.MerchantTellerDto;
import com.iwomi.authms.frameworks.data.entities.MerchantTeller;
import com.iwomi.authms.frameworks.data.entities.UserEntity;
import com.iwomi.authms.services.merchantTellers.MerchantTellerService;
import com.iwomi.authms.services.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "${apiV1Prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MerchantTellerService merchantTellerService;

    @PostMapping("/{id}/change-pin")
    @Operation(
            description = "Change user pin",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> changePin(@PathVariable UUID id, @RequestParam("pin") String pin) {
        User result = userService.updatePin(id, pin);
        return GlobalResponse.responseBuilder("User pin update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }



    @PostMapping("/{id}/change-status")
    @Operation(
            description = "Change user status",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam("status") StatusEnum status) {
        User result = userService.updateStatus(id, status);
        return GlobalResponse.responseBuilder("User status update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping("/{id}/change-password")
    @Operation(
            description = "Change user password",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> changePassword(@PathVariable UUID id, @RequestParam("password") String password) {
        User result = userService.updatePassword(id, password);
        return GlobalResponse.responseBuilder("User password update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping("/{clientCode}/check-pin")
    public  ResponseEntity<?> checkPin(@PathVariable String clientCode, @RequestParam("pin") String pin){
        boolean isValid = userService.isValidPin(clientCode, pin);
        if(isValid){
            return  ResponseEntity.ok(isValid);
        } else {
            return ResponseEntity.status(404).body("Invalid PIN");
        }
    }

    @GetMapping("/search-merchant-teller")
    public ResponseEntity<List<MerchantTeller>> searchByUser(@RequestParam("phone") String phone){
        System.out.println("in search by phone number");
        return ResponseEntity.ok(merchantTellerService.findWithPhone(phone));
    }

    @PostMapping("/merchant-teller")
    public ResponseEntity<MerchantTeller> createMerchantTeller(@RequestBody MerchantTellerDto merchantTellerDto) {
        System.out.println("merchant data: "+merchantTellerDto.toString());
        return new ResponseEntity<>(merchantTellerService.create(merchantTellerDto), HttpStatus.CREATED);
    }

    @GetMapping("/deleted")
    public  ResponseEntity<?> showByRoleAndDeleted(@RequestParam("role") UserTypeEnum role) {
        List<String> users = userService.viewByRoleAndDeleted(role);
        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @GetMapping()
    public  ResponseEntity<?> showByRole(@RequestParam UserTypeEnum role) {
        List<String> users = userService.viewByRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


}
