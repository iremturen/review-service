package com.biblioteca.review_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer bookId;
    private Integer userId;
    private String review;
    private Date createdAt;
    private Date updatedAt;

}
