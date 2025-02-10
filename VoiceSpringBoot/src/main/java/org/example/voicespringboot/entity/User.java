package org.example.voicespringboot.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.voicespringboot.enums.AuthProvider;
import org.example.voicespringboot.enums.Role;
import org.hibernate.annotations.GenericGenerator;


import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = "recentSongs")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="email",unique = true,nullable = false)
    private String email;


    private Role role=Role.USER;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RecentSongs> recentSongs;
}
