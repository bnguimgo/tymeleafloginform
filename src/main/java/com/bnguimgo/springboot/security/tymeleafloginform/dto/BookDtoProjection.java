package com.bnguimgo.springboot.security.tymeleafloginform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
@EqualsAndHashCode
@Builder
public class BookDtoProjection implements Serializable {

    private Long id;

    private String title;
    private String isbn;
    //@JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    //@JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;
}