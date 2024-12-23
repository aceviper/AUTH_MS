package com.iwomi.authms.frameworks.data.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "merchant_tellers")
public class MerchantTeller extends BaseEntity{
    private String userId;
    private String merchantId;
    private String organisationName;
}
