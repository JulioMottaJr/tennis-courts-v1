package com.tenniscourts.guests;


import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tennis-court-guest")
@Api
public class GuestController extends BaseRestController {
    private final GuestService guestService;

    @PostMapping("/addguest/{adminID}")
    @ApiOperation(value = "Add guest")
    public ResponseEntity<Void> addGuest(@PathVariable Long adminID, @RequestBody GuestDto guestDto) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(adminID, guestDto).getId())).build();
    }

    @PutMapping("/updateguest/{adminID}")
    @ApiOperation(value = "Update guest")
    public ResponseEntity<GuestDto> updateGuest(@PathVariable Long adminID, @RequestBody GuestDto guestDto) {
        return ResponseEntity.ok(guestService.updateGuest(adminID, guestDto));
    }

    @GetMapping("/findbyguestid/{adminID}/{guestID}")
    @ApiOperation(value = "Find guest by ID")
    public ResponseEntity<GuestDto> findByID(@PathVariable Long adminID, @PathVariable Long guestID) {
        return ResponseEntity.ok(guestService.findByID(adminID, guestID));
    }

    @GetMapping("/findAll/{adminID}")
    @ApiOperation(value = "Find all guests")
    public ResponseEntity<List<GuestDto>> findAllGuests(@PathVariable Long adminID) {
        return new ResponseEntity<List<GuestDto>>(guestService.findAllGuests(adminID),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{adminID}/{guestID}")
    @ApiOperation(value = "Delete guest")
    public ResponseEntity<GuestDto> delete(@PathVariable Long adminID, @PathVariable Long guestID) {
        return ResponseEntity.ok(guestService.delete(adminID, guestID));
    }

    @GetMapping("/findbyname/{adminID}/{name}")
    @ApiOperation(value = "Find guests by name")
    public ResponseEntity<List<GuestDto>> findByName(@PathVariable Long adminID, @PathVariable String name) {
        return new ResponseEntity<List<GuestDto>>(guestService.findGuestByName(adminID, name),HttpStatus.OK);
    }
}
