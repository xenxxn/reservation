package com.example.reservation.service;
import com.example.reservation.dto.Auth;
import com.example.reservation.dto.partner.PartnerDto;
import com.example.reservation.dto.store.StoreAddDto;
import com.example.reservation.dto.store.StoreDto;
import com.example.reservation.dto.store.StoreUpdatingDto;
import com.example.reservation.entity.PartnerEntity;
import com.example.reservation.entity.StoreEntity;
import com.example.reservation.exception.impl.*;
import com.example.reservation.repository.PartnerRepository;
import com.example.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PartnerService implements UserDetailsService {
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String partnerId) throws UsernameNotFoundException {
        return this.partnerRepository.findByPartnerId(partnerId)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find partner -> " + partnerId));
    }

    public PartnerDto register(Auth.PartnerSignUp partner) {
        boolean exists = this.partnerRepository.existsByPartnerId(partner.getPartnerId());
        if (exists) {
            throw new AlreadyExistMemberException();
        }
        partner.setPassword(this.passwordEncoder.encode(partner.getPassword()));
        PartnerEntity partnerEntity =  this.partnerRepository.save(partner.toEntity());
        return PartnerDto.fromEntity(partnerEntity);
    }

    public PartnerEntity authenticate(Auth.PartnerSignIn partner) {
        var user = this.partnerRepository.findByPartnerId(partner.getPartnerId())
                .orElseThrow(NotExistIdException::new);
        if (!this.passwordEncoder.matches(partner.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }
        return user;
    }

    public StoreDto addStore(String partnerId, StoreAddDto.Request request) {
        PartnerEntity partnerEntity = partnerRepository.findByPartnerId(partnerId)
                .orElseThrow(NotExistIdException::new);
        this.requestValidate(partnerId, request.getStoreName());

        StoreEntity storeEntity = storeRepository.save(
                StoreAddDto.Request.toEntity(request, partnerId)
        );

        partnerEntity.setStore(storeEntity.getId(), storeEntity.getStoreName());
        partnerRepository.save(partnerEntity);

        return StoreDto.fromEntity(storeEntity);
    }

    public StoreDto updateStore(String partnerId, StoreUpdatingDto.Request request) {
        if (!partnerRepository.existsByPartnerId(partnerId)) {
            throw new NotExistIdException();
        }

        StoreEntity storeEntity = storeRepository.findByPartnerId(partnerId)
                .orElseThrow(NoStoreException::new);

        if (!storeEntity.getStoreName().equals(request.getStoreName()) &&
        storeRepository.existsByStoreName(request.getStoreName())) {
            throw new AlreadyExistStoreException();
        }
        storeEntity.edit(request);
        StoreEntity updateStore = storeRepository.save(storeEntity);
        return StoreDto.fromEntity(updateStore);
    }


    public void requestValidate(String partnerId, String storeName) {
        if (storeRepository.existsByPartnerId(partnerId)) {
            throw new AlreadyExistStoreException();
        } else if (storeRepository.existsByStoreName(storeName)) {
            throw new AlreadyExistStoreException();
        }
    }
}
