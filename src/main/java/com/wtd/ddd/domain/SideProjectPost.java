package com.wtd.ddd.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
public class SideProjectPosts {

    @Id
    @GeneratedValue()
    private Long id;
}
