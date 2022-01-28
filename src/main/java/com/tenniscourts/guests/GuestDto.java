package com.tenniscourts.guests;

import lombok.*;

import javax.persistence.Column;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {

    private Long id;
    @Column
    private String name;

}
