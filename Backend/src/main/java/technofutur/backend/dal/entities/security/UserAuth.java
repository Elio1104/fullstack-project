package technofutur.backend.dal.entities.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import technofutur.backend.dal.entities.BaseEntity;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "USER_AUTH")
public class UserAuth extends BaseEntity<Long> implements UserDetails {

    @Getter @Setter
    @Column(name = "USERNAME",unique = true, nullable = false, length = 50)
    private String username;

    @Getter @Setter
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Getter @Setter
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
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
