package com.iwomi.authms.frameworks.externals.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "nofia-service", url = "${externals.base-url.nofia}")
public interface NofiaClient {

   @PostMapping("/validations")
   ResponseEntity<?> sendToValidation(@RequestParam("clientCode") String clientCode);

//   @GetMapping("/{role}/deleted")
//   ResponseEntity<?>  getUsersByRoleAndDeleted(@RequestParam String role);
//
//   @GetMapping("/{role}")
//   ResponseEntity<?> getUsersByRole(@RequestParam UserTypeEnum role);
}
