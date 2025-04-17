package com.PseudoNerds.LandNFT.Controller;

import com.PseudoNerds.LandNFT.Entity.LandTransfer;
import com.PseudoNerds.LandNFT.Service.LandTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Land-transfer")
@RequiredArgsConstructor
public class LandTransferController {
    @Autowired
    private LandTransferService landTransferService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferLand(@RequestBody LandTransfer landTransfer, @RequestParam String privateKey) {
        try {
//            // Create LandTransfer object from request
//            LandTransfer transfer = new LandTransfer();
//            transfer.setTokenId(landTransfer.getTokenId());
//            transfer.setToAddress(landTransfer.getToAddress());

            String txHash = landTransferService.transferLand(landTransfer, privateKey);

            return ResponseEntity.ok("Transaction successful. Hash: " + txHash);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Transfer failed: " + e.getMessage());
        }
    }
}

