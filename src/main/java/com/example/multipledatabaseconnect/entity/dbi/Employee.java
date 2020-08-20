package com.example.multipledatabaseconnect.entity.dbi;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long fid;

}
