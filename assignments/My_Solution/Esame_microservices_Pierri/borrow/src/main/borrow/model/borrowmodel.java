package org.pierri.borrow.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "borrowings")
public class Borrow {

    @Id
    private Long borrowId;

    private String bookId;

    private String customerId;

    private String notifyToPhoneNr;

}