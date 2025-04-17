package com.PseudoNerds.LandNFT.Controller;

import com.PseudoNerds.LandNFT.Entity.LandDetails;
import com.PseudoNerds.LandNFT.Entity.User;
import com.PseudoNerds.LandNFT.Service.LandDetailsService;
import com.PseudoNerds.LandNFT.Service.LandRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/Land")
public class LandController {
    Logger log= LoggerFactory.getLogger(LandController.class);

    @Autowired
    private LandRegisterService landRegisterService;


    @Autowired
    private LandDetailsService landDetailsService;


    @PostMapping("/RegisterLand")
    public ResponseEntity<String> registerLand(@RequestBody LandDetails landDetails)throws  Exception{
        try {
            String txHash = landRegisterService.registerLand(landDetails);
            return ResponseEntity.ok("Land registered successfully. Transaction Hash: " + txHash);
        } catch (Exception e) {
            log.error("❌ Error registering land: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error registering land: " + e.getMessage());
        }
    }


    @GetMapping("/getLandDetails")
    public ResponseEntity<Optional<LandDetails>> getLandDetailsByPropertyId(@RequestParam String propertyId)throws Exception{
        Optional<LandDetails> landDetails=landDetailsService.getLandDetailsByPropertyId(propertyId);
        if(!landDetails.isEmpty()){ return ResponseEntity.ok(landDetails);}
        return new ResponseEntity<>(HttpStatusCode.valueOf(500));

    }

    @GetMapping("/getUserByPropertyId")
    public ResponseEntity<Set<Optional<User>>> getUserByPropertyId(@RequestParam String propertyId)throws Exception{
        Set<Optional<User>> user=landDetailsService.getUserByPropertyId(propertyId);
        if(!user.isEmpty()){
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));

    }
}
