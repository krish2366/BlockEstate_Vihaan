package com.PseudoNerds.LandNFT.Controller;

import com.PseudoNerds.LandNFT.Entity.TransactionHash;
import com.PseudoNerds.LandNFT.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/Transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/getTransactionHash")
    public ResponseEntity<Optional<TransactionHash>> getTransactionHash(@RequestParam String propertyId){
        Optional<TransactionHash> transactionHash=transactionService.getTransactionHash(propertyId);
        if(!transactionHash.isEmpty()){
            return ResponseEntity.ok(transactionHash);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

}
