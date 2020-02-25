package com.mybatis.service.entity;

import lombok.Data;

@Data
public class PageRequest {

    private Integer pageNumber = 1;

    private Integer pageSize = 10;

}
