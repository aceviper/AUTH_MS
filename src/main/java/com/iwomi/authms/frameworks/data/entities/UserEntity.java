package com.iwomi.authms.frameworks.data.entities;

import com.iwomi.authms.core.enums.StatusEnum;
import com.iwomi.authms.core.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class UserEntity extends BaseEntity implements UserDetails {
    private String email;
    private String password;
    private String pin;

    @Column(name = "phone_number") private String phoneNumber;

    @Enumerated(EnumType.STRING) private StatusEnum status;
    @Enumerated(EnumType.STRING) private UserTypeEnum role;

    private String verificationCode;
    private Date codeSendDate;
    private  String clientCode;     // role "UserTypeEnum.USER" does not have client code
    private String client_id;   // client foreign key not in use
    private boolean firstSignIn = Boolean.TRUE;

    private boolean deleted = Boolean.FALSE;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return phoneNumber;
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
