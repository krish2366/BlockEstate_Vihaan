package com.PseudoNerds.LandNFT.Service;

import com.PseudoNerds.LandNFT.Entity.TransactionHash;
import com.PseudoNerds.LandNFT.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Optional<TransactionHash> getTransactionHash(String propertyId){
        Optional<TransactionHash> transactionHash=transactionRepository.findByPropertyId(propertyId);
        return transactionHash;
    }
}
