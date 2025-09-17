package com.example.BlogApplication.Entity;


import com.example.BlogApplication.Entity.Type.AuthProviderType;
import com.example.BlogApplication.Entity.Type.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"posts", "comments", "likes"})
@Table(name = "users")
public class User extends  BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
     private String username;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = true)
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments=new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Like> likes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role= Role.USER;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;
}
