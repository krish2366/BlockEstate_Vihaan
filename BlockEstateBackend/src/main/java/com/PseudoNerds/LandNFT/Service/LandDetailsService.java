package com.PseudoNerds.LandNFT.Service;


import com.PseudoNerds.LandNFT.Entity.LandDetails;
import com.PseudoNerds.LandNFT.Entity.User;
import com.PseudoNerds.LandNFT.Repository.LandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LandDetailsService {
    @Autowired
    private LandRepository landRepository;

    @Autowired
    private UserService userService;

    public Optional<LandDetails> getLandDetailsByPropertyId(String propertyId) throws Exception{
        Optional<LandDetails> landDetails=landRepository.findByPropertyId(propertyId);
//        if(landDetails.isEmpty()){
//            throw new Exception("Property is Not yet Registered Or PropertyId is wrong!");
//        }
        return  landDetails;
    }

    public Set<Optional<LandDetails>> getLandDetailsByownerWalletAddress(String ownerWalletAddress) {

        return landRepository.findByownerWalletAddress(ownerWalletAddress);
    }

    public Set<Optional<User>> getUserByPropertyId(String propertyId) throws Exception{
        Optional<LandDetails> landDetails=getLandDetailsByPropertyId(propertyId);
        LandDetails landDetails1=landDetails.get();
        String ownerWalletAddress=landDetails1.getOwnerWalletAddress();
        Set<Optional<User>> user=userService.getUserByownerWalletAddress(ownerWalletAddress);
        return user;

    }
}
