package com.bnguimgo.springboot.security.tymeleafloginform.modele;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Validated
public class User implements Serializable {

    private Long id;

    @NotBlank(message = "Le prénom utilisateur est obligatoire")
    @NotEmpty(message = "Le prénom utilisateur ne peut être vide")
    @NotNull(message = "Le prénom utilisateur ne peut être nul")
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank(message = "Le nom utilisateur est obligatoire")
    private String lastName;
    private String email;
    private boolean actif;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;
    private Set<String> roles;
}
