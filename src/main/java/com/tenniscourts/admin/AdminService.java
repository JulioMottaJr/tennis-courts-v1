package com.tenniscourts.admin;


import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final AdminMapper adminMapper;

    public AdminDto addAdmin(AdminDto adminDTO) {

        return adminMapper.map(adminRepository.saveAndFlush(adminMapper.map(adminDTO)));
    }
}
