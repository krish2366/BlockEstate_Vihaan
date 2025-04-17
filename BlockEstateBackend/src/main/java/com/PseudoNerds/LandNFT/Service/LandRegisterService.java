package com.PseudoNerds.LandNFT.Service;

import com.PseudoNerds.LandNFT.Entity.LandDetails;
import com.PseudoNerds.LandNFT.Entity.TransactionHash;
import com.PseudoNerds.LandNFT.Entity.User;
import com.PseudoNerds.LandNFT.Repository.LandRepository;
import com.PseudoNerds.LandNFT.Repository.TransactionRepository;
import com.PseudoNerds.LandNFT.Web3Service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LandRegisterService {

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private Web3j web3j;

    @Autowired
    private TransactionRepository transactionRepository;

//    @Value("${blockchain.contractAddress}")
    private String contractAddress="0x897be5aa988a7871bc5fc575d3009ca8171bff9f";

//    @Value("${blockchain.fromAddress}")
    private String fromAddress="0x102b8da879993a1f08f78565f0213150355e0c23";

    @Autowired
    private BlockchainService blockchainService;

    public String registerLand(LandDetails landDetails) throws Exception{
        String ownerWallet = landDetails.getOwnerWalletAddress();

        if (isPropertyAlreadyRegistered(landDetails)) {
            throw new IllegalStateException("This property is already registered on the blockchain.");
        }

        if (ownerWallet == null || ownerWallet.isEmpty()) {
            throw new IllegalArgumentException("Owner wallet address cannot be null or empty");
        }
        Address owner = new Address(ownerWallet.toLowerCase());
        Function function=new Function("registerLand",
                Arrays.asList(owner,
                        new Utf8String(landDetails.getTokenURI()),
                        new Utf8String(landDetails.getPropertyId()),
                        new Utf8String(landDetails.getLocationDescription() != null ? landDetails.getLocationDescription() : ""),
                        new Utf8String(landDetails.getLatitudeLongitude()),
                        new Uint256(BigInteger.valueOf(landDetails.getAreaSqMeters()))),Arrays.asList());

        String encodedFunction= FunctionEncoder.encode(function);

        TransactionReceipt transactionReceipt=blockchainService.sendTransaction(encodedFunction);

        landDetails.setRegistrationDate(java.time.LocalDateTime.now());

        landRepository.save(landDetails);

        String transactionHash= transactionReceipt.getTransactionHash();
        TransactionHash transactionHash1=new TransactionHash(landDetails.getPropertyId(),transactionHash);
        transactionRepository.save(transactionHash1);

        return  transactionHash;


    }
//    private boolean isPropertyAlreadyRegistered(String propertyId) throws Exception {
//        Function function = new Function(
//                "isPropertyRegistered",
//                List.of(new Utf8String(propertyId)),
//                List.of(new TypeReference<org.web3j.abi.datatypes.Bool>() {})
//        );
//
//        String encodedFunction = FunctionEncoder.encode(function);
//
//        EthCall response = web3j.ethCall(
//                Transaction.createEthCallTransaction(fromAddress, contractAddress, encodedFunction),
//                DefaultBlockParameterName.LATEST
//        ).send();
//
//        List<Type> output = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
//
//        if (output.isEmpty()) {
//            throw new RuntimeException("Empty response from blockchain when checking property ID");
//        }
//
//        return (Boolean) output.get(0).getValue();
//    }

    private boolean isPropertyAlreadyRegistered(LandDetails landDetails){
        String propertyId=landDetails.getPropertyId();
        Optional<LandDetails> landDetails1=landRepository.findByPropertyId(propertyId);
        if(landDetails1.isEmpty())return false;
        return true;

    }







}
