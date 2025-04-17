package com.PseudoNerds.LandNFT.Service;

import com.PseudoNerds.LandNFT.Entity.LandTransfer;
import com.PseudoNerds.LandNFT.Entity.TransactionHash;
import com.PseudoNerds.LandNFT.Repository.LandTransferRepository;
import com.PseudoNerds.LandNFT.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LandTransferService {

    private Web3j web3j;


    private String contractAddress;

    @Autowired
    private LandTransferRepository landTransferRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    public LandTransferService(Web3j web3j) {
        this.web3j = web3j;
    }



    @Transactional
    public String transferLand(LandTransfer landTransfer,String privateKey)throws Exception {

        Long tokenId = landTransfer.getTokenId();
        String toAddress = landTransfer.getToAddress();

        // Load credentials of sender (msg.sender)
        Credentials credentials = Credentials.create(privateKey);
        // Instead of fixed gas limit
        BigInteger gasLimit = BigInteger.valueOf(300_000);

// Use dynamic estimation
        String encodedFunction = null;
        EthEstimateGas estimateGas = web3j.ethEstimateGas(
                Transaction.createEthCallTransaction(
                        credentials.getAddress(),
                        contractAddress,
                        encodedFunction
                )
        ).send();
        BigInteger gasPrice = estimateGas.getAmountUsed();


        // Get nonce
        BigInteger nonce = web3j.ethGetTransactionCount(
                credentials.getAddress(),
                DefaultBlockParameterName.LATEST
        ).send().getTransactionCount();

        // Prepare the function call (only toAddress and tokenId needed)
        Function function = new Function(
                "transferLand",
                Arrays.asList(new Address(toAddress), new Uint256(tokenId)),
                Collections.emptyList()
        );

        encodedFunction = FunctionEncoder.encode(function);

        // Build raw transaction
        RawTransaction transaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress,
                BigInteger.ZERO,
                encodedFunction
        );

        // Sign and send
        byte[] signedMessage = TransactionEncoder.signMessage(transaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction response = web3j.ethSendRawTransaction(hexValue).send();

        if (response.hasError()) {
            throw new RuntimeException("Transaction Error: " + response.getError().getMessage());
        }

        // Save to DB
        landTransfer.setFromAddress(credentials.getAddress()); // derived from private key
        landTransfer.setTransferTimestamp(LocalDateTime.now());
        landTransferRepository.save(landTransfer);

        String transactionHash = response.getTransactionHash();
        TransactionHash transactionHash1 = new TransactionHash(landTransfer.getPropertyId(), transactionHash);
        transactionRepository.save(transactionHash1);


        return transactionHash;
    }



}
