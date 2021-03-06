package com.example.walletapp.services;

import com.example.walletapp.exceptions.UserAlreadyHasWalletException;
import com.example.walletapp.exceptions.UserDoesNotExistException;
import com.example.walletapp.exceptions.WalletException;
import com.example.walletapp.models.WalletUser;
import com.example.walletapp.models.Wallet;
import com.example.walletapp.repositories.WalletUserRepository;
import com.example.walletapp.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletUserRepository walletUserRepository;

    @Transactional
    public Wallet createWallet(Long walletUserId, Wallet wallet) throws UserDoesNotExistException, UserAlreadyHasWalletException {

        WalletUser walletUser = null;

        try {
             walletUser = walletUserRepository.findById(walletUserId).orElse(null);
            System.out.println(walletUser.getWallet());

        }catch (NullPointerException ex){

        }

        try {
            if (walletUser == null) {
                throw new UserDoesNotExistException(walletUserId);
            }
        }catch (NullPointerException ex){

        }
        try {
            if (walletUser.getWallet() != null) {
                throw new UserAlreadyHasWalletException(walletUser);
            }
        }catch (NullPointerException ex){

        }

        wallet.setAccountNumber(walletUser.getPhoneNumber());
        wallet.setWalletUser(walletUser);
        return  walletRepository.save(wallet);
    }

    public boolean delete(Long id){
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            walletRepository.delete(wallet.get());
            return true;
        }
        throw new WalletException("wallet with " + id + " does not exit");
    }


}
