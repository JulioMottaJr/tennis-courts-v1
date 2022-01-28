package com.tenniscourts.admin;

import com.tenniscourts.guests.Guest;
import lombok.*;

import javax.persistence.Column;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    private Long id;
    @Column
    private String name;

}
