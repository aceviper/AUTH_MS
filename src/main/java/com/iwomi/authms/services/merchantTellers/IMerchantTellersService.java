package com.iwomi.authms.services.merchantTellers;

import com.iwomi.authms.dtos.MerchantTellerDto;
import com.iwomi.authms.frameworks.data.entities.MerchantTeller;

import java.util.List;
import java.util.UUID;

public interface IMerchantTellersService {
    MerchantTeller create(MerchantTellerDto merchantTellerDto);

    List<MerchantTeller> findWithPhone(String phone);
}
