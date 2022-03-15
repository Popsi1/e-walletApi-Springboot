package com.example.walletapp.services;

import com.example.walletapp.constant.Constants;
import com.example.walletapp.exceptions.KycAlreadyExistException;
import com.example.walletapp.exceptions.UserDoesNotExistException;
import com.example.walletapp.exceptions.WalletIdDoesNotExistException;
import com.example.walletapp.models.KycMaster;
import com.example.walletapp.models.KycUltimate;
import com.example.walletapp.models.WalletUser;
import com.example.walletapp.repositories.KycMasterRepository;
import com.example.walletapp.repositories.KycUltimateRepository;
import com.example.walletapp.repositories.WalletUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KycService {

    @Autowired
    private WalletUserRepository walletUserRepository;

    @Autowired
    private KycMasterRepository kycMasterRepository;

    @Autowired
    private KycUltimateRepository kycUltimateRepository;


    @Transactional
    public void createKycForMasterVerification(KycMaster kycMaster, Long walletUserId, String kycLevel) throws Exception {

        WalletUser walletUser = walletUserRepository.findById(walletUserId).orElse(null);

        if (walletUser == null) {
            throw new UserDoesNotExistException(walletUserId);
        }

        if (walletUser.getWallet() == null) {
            throw new WalletIdDoesNotExistException(walletUser.getWallet().getId());
        }

        if (walletUser.getWallet().getKycMaster() != null) {
            throw new KycAlreadyExistException(walletUser.getWallet().getKycMaster().getId());
        }

        if(kycLevel.equals(Constants.MASTER)){
            kycMaster.setWallet(walletUser.getWallet());
            kycMasterRepository.save(kycMaster);
        }else throw new Exception("cannot verify");

    }

    @Transactional
    public void createKycForUltimateVerification(KycUltimate kycUltimate, Long walletUserId, String kycLevel) throws Exception {

        WalletUser walletUser = walletUserRepository.findById(walletUserId).orElse(null);

        if (walletUser == null) {
            throw new UserDoesNotExistException(walletUserId);
        }

        if (walletUser.getWallet() == null) {
            throw new WalletIdDoesNotExistException(walletUser.getWallet().getId());
        }

        if (walletUser.getWallet().getKycUltimate() != null) {
            throw new KycAlreadyExistException(walletUser.getWallet().getKycUltimate().getId());
        }

        if(kycLevel.equals(Constants.ULTIMATE)){
            kycUltimate.setWallet(walletUser.getWallet());
            kycUltimateRepository.save(kycUltimate);
        }else throw new Exception("cannot verify");
    }
}
