package com.iwomi.authms.services.merchantTellers;

import com.iwomi.authms.core.enums.UserTypeEnum;
import com.iwomi.authms.core.errors.exceptions.UnAuthorizedException;
import com.iwomi.authms.dtos.MerchantTellerDto;
import com.iwomi.authms.frameworks.data.entities.MerchantTeller;
import com.iwomi.authms.frameworks.data.entities.UserEntity;
import com.iwomi.authms.frameworks.data.repositories.merchantTellers.MerchantTellersRepository;
import com.iwomi.authms.frameworks.data.repositories.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.iwomi.authms.core.mappers.IMerchantTellerMapper;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantTellerService implements IMerchantTellersService{

    private final IMerchantTellerMapper mapper;
    private final MerchantTellersRepository repository;
    private final UserRepository userRepository;

    @Override
    public MerchantTeller create(MerchantTellerDto merchantTellerDto) {
        return repository.create(mapper.mapToEntity(merchantTellerDto));
    }

    @Override
    public List<MerchantTeller> findWithPhone(String phone) {
        UserEntity user = userRepository.getOneByPhone(phone);

        if (user.getRole().equals(UserTypeEnum.MERCHANT))
            throw new UnAuthorizedException("you are not permitted to add this account");

        return repository.findWithUserId(String.valueOf(user.getUuid()));

    }
}
