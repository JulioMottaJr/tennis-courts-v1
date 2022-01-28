package com.tenniscourts.guests;


import com.tenniscourts.admin.AdminRepository;
import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class GuestService {

    private final AdminRepository adminRepository;

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDto addGuest(Long adminID, GuestDto guestDto) {

        if (!adminRepository.findById(adminID).isPresent()) {
            throw new EntityNotFoundException("AdminID was not found");
        }

        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDto)));

    }

    public GuestDto updateGuest(Long adminID, GuestDto guestDto) {

        if (!adminRepository.findById(adminID).isPresent()) {
            throw new EntityNotFoundException("AdminID was not found");
        } else {
            Guest guest = guestRepository.findById(guestDto.getId()).orElseThrow(() -> new EntityNotFoundException("GuestID was not found"));
            guest.setName(guestDto.getName());
            guestRepository.save(guest);

            return guestMapper.map(guest);
        }



    }

    public GuestDto findByID (Long adminID,Long guestId) {
        if (!adminRepository.findById(adminID).isPresent()) {
            throw new EntityNotFoundException("AdminID was not found");
        } else {


            return guestMapper.map(guestRepository.findById(guestId).orElseThrow(()->new EntityNotFoundException("AdminID was not found")));
        }
    }

    public GuestDto delete (Long adminID,Long guestId) {
        if (!adminRepository.findById(adminID).isPresent()) {
            throw new EntityNotFoundException("AdminID was not found");
        } else {
        Guest guest = guestRepository.findById(guestId).orElseThrow(()-> new EntityNotFoundException("Guest was not found"));
        guestRepository.deleteById(guest.getId());
            return guestMapper.map(guest);
        }
    }

    public List<GuestDto> findAllGuests(Long adminID){

        if (!adminRepository.findById(adminID).isPresent()) {
            throw new EntityNotFoundException("AdminID was not found");
        } else {
            List<Guest> guests = guestRepository.findAll();
            return guestMapper.map(guests);

        }
    }

    public List<GuestDto> findGuestByName(Long adminID, String name){

        if (!adminRepository.findById(adminID).isPresent()) {
            throw new EntityNotFoundException("AdminID was not found");
        } else {
            List<Guest> guests = guestRepository.findByNameContaining(name);
            return guestMapper.map(guests);

        }
    }
}
