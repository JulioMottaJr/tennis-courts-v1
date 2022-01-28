package com.tenniscourts.admin;

import com.tenniscourts.config.BaseRestController;

import com.tenniscourts.tenniscourts.TennisCourtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tennis-court-admin")
@Api
public class AdminController extends BaseRestController {
    private final AdminService adminService;

    @PostMapping
    @ApiOperation(value = "Add Admin")
    public ResponseEntity<Void> addAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.created(locationByEntity(adminService.addAdmin(adminDto).getId())).build();
    }
}
