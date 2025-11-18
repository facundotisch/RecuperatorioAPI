package com.uade.tpo.marketplace.controllers.auth;

import com.uade.tpo.marketplace.entity.Compra;
import com.uade.tpo.marketplace.enums.Estados;
import com.uade.tpo.marketplace.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRequest {
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private int dni;
    private Role role;
    private Estados estado;
}
