package com.PseudoNerds.LandNFT.Web3Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

@Slf4j
@Service
public class BlockchainService {
    private final Web3j web3j;
    private final Credentials credentials;

//    @Value("${ethereum.contractAddress}")
   private String contractAddress;

//    @Value("https://sepolia.infura.io/v3/3a699b648b924eaf8f0263bbb862578e")
//    private String rpcUrl;

    public BlockchainService(@Value("${blockchain.private.key}") String privateKey) {
        String rpcUrl="https://sepolia.infura.io/v3/3a699b648b924eaf8f0263bbb862578e";
        
         contractAddress="0x897be5aa988a7871bc5fc575d3009ca8171bff9f";
//        System.out.println("📢 BlockchainService initialized");
//        System.out.println("🔑 Injected private key: " + privateKey);
//        System.out.println("🌐 RPC URL: " + rpcUrl);
//        System.out.println("🏷️ Contract Address: " + contractAddress);

        if (privateKey == null || privateKey.isBlank()) {
            log.error("Private Key Not Configured");
            throw new IllegalStateException("Private key must be configured");
        }

        if (rpcUrl == null || rpcUrl.isBlank()) {
            log.error("RPC URL Not Configured");
            throw new IllegalStateException("RPC URL must be configured");
        }

        if (contractAddress == null || contractAddress.isBlank()) {
            log.error("Contract Address Not Configured");
            throw new IllegalStateException("Contract address must be configured");
        }

        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.credentials = Credentials.create(privateKey);
    }

    public Web3j web3j() {
        return web3j;
    }

//    public String getContractAddress() {
//        return contractAddress;
//    }

    public Credentials getCredentials() {
        return credentials;
    }

    public TransactionReceipt sendTransaction(String encodedFunction) throws Exception {
        BigInteger nonce = web3j.ethGetTransactionCount(
                        credentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send()
                .getTransactionCount();

        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        BigInteger gasLimit = DefaultGasProvider.GAS_LIMIT;

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce, gasPrice, gasLimit, contractAddress, encodedFunction);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        if (ethSendTransaction.hasError()) {
            String errorMessage = ethSendTransaction.getError().getMessage();
            log.error("Transaction failed: {}", errorMessage);
            throw new RuntimeException("Transaction error: " + errorMessage);
        }

        String transactionHash = ethSendTransaction.getTransactionHash();

        // Wait for transaction to be mined
        return waitForReceipt(transactionHash);
    }

    private TransactionReceipt waitForReceipt(String transactionHash) throws IOException, InterruptedException {
        int attempts = 40;
        int sleepDuration = 3000; // 3 seconds
        for (int i = 0; i < attempts; i++) {
            try {
                TransactionReceipt receipt = web3j.ethGetTransactionReceipt(transactionHash)
                        .send()
                        .getTransactionReceipt()
                        .orElse(null);

                if (receipt != null) {
                    return receipt;
                }

                log.info("Transaction receipt not found, attempt {}/{}", i + 1, attempts);
                Thread.sleep(sleepDuration);
            } catch (IOException | InterruptedException e) {
                log.error("Error waiting for transaction receipt", e);
                throw e;
            }
        }
        throw new RuntimeException("Transaction receipt not received after waiting");
    }
}
