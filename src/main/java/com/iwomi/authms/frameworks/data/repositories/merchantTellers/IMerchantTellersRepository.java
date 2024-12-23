package com.iwomi.authms.frameworks.data.repositories.merchantTellers;

import com.iwomi.authms.frameworks.data.entities.MerchantTeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IMerchantTellersRepository extends JpaRepository<MerchantTeller, UUID> {

    List<MerchantTeller> findByUserId(String userId);
}
