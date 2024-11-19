package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class Pessoas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    // Construtores

    public Pessoas() {
    }

    public Pessoas(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    // O 'id' é gerado automaticamente, não precisamos de um setter para ele

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Nunca exponha a senha em texto plano
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}