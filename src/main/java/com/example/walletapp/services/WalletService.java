package com.example.walletapp.services;

import com.example.walletapp.exceptions.UserAlreadyHasWalletException;
import com.example.walletapp.exceptions.UserDoesNotExistException;
import com.example.walletapp.models.WalletUser;
import com.example.walletapp.models.Wallet;
import com.example.walletapp.repositories.WalletUserRepository;
import com.example.walletapp.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletUserRepository walletUserRepository;


//    public Wallet getById(Long id){

//        Optional<Wallet> wallet = walletRepository.findById(id);
//        if(wallet.isPresent()){
//            return wallet.get();
//        }
//        throw new WalletException("wallet with " + id + " does not exit");
//    }

    // use dto, accountnumeber must be equal to phone number
    public Wallet createWallet(Long walletUserId, Wallet wallet) throws UserDoesNotExistException, UserAlreadyHasWalletException {

        WalletUser walletUser = walletUserRepository.findById(walletUserId).orElse(null);

        if (walletUser == null) {
            throw new UserDoesNotExistException(walletUserId);
        }

        if (walletUser.getWallet() != null) {
            throw new UserAlreadyHasWalletException(walletUser);
        }

        wallet.setWalletUser(walletUser);
        return  walletRepository.save(wallet);
    }




//    public boolean delete(Long id){
//        Optional<Wallet> wallet = walletRepository.findById(id);
//        if(wallet.isPresent()){
//            walletRepository.delete(wallet.get());
//            return true;
//        }
//        throw new WalletException("wallet with " + id + " does not exit");
//    }
}