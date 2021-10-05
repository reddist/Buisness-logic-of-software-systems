package io.reddist.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import io.reddist.serializers.UserSerializer;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(
    name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames={"email"})
)
@JsonSerialize(using = UserSerializer.class)
public class User implements Serializable, UserDetails {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
    @Id
    private long userId;
    @Transient
    private static final long serialVersionUID = 4L;
    @Nullable
    @Email
    private String email;
    @Nullable
    private String password;
    @Nullable
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER) private List<JobApply> jobApplies;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER) private List<CV> CVs;

    public User() {}

    public User(@Nullable String email, @Nullable String password) {
        this.email = email;
        this.password = password;
    }

    public User(@Nullable String email, @Nullable String password, @Nullable String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("User"));
    }

    @Nullable
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}