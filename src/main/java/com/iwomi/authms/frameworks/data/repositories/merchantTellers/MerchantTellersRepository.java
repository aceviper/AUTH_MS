package com.iwomi.authms.frameworks.data.repositories.merchantTellers;

import com.iwomi.authms.frameworks.data.entities.MerchantTeller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MerchantTellersRepository {
    private final IMerchantTellersRepository repository;

    public MerchantTeller create(MerchantTeller merchantTeller){
        return repository.save(merchantTeller);
    }

    public List<MerchantTeller> findWithUserId(String userId){
        return repository.findByUserId(userId);
    }
}
