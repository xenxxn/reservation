package com.example.reservation.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity(name = "partner")
public class PartnerEntity implements UserDetails {

    @Id
    @Column(unique = true, name = "partner_id")
    private String partnerId;

    private String password;

    @Column(name = "partner_name")
    private String partnerName;

    private String phone;

    @Column(name = "member_type")
    private String memberType;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    private Long storeId;
    private String storeName;

    public void setStore(Long storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("PARTNER"));
        return grantedAuthorityList;
    }

    @Override
    public String getUsername() {
        return this.partnerId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
