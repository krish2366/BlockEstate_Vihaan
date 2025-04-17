package com.PseudoNerds.LandNFT.Controller;

import com.PseudoNerds.LandNFT.Entity.LandDetails;
import com.PseudoNerds.LandNFT.Entity.User;
import com.PseudoNerds.LandNFT.Repository.UserRepository;
import com.PseudoNerds.LandNFT.Service.UserService;
import com.PseudoNerds.LandNFT.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        Optional<User> user1=userRepository.findByownerWalletAddress(user.getOwnerWalletAddress());
        if(!user1.isEmpty())return new ResponseEntity<>(HttpStatusCode.valueOf(402));
        userService.registerUser(user);
        String username= user.getUsername();
        String password=user.getPassword();
        String token= jwtUtil.generateToken(username,password);
        if(jwtUtil.isTokenValid(token)) return  ResponseEntity.ok(user);

        return new ResponseEntity<>(HttpStatusCode.valueOf(402));
    }

    @GetMapping("/getUserById")
    public ResponseEntity<Optional<User>> getUserById(@RequestParam Long id){
        Optional<User> user=userService.getUserById(id);
        if(!user.isEmpty()){
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @GetMapping("/getLandDetailsById")
    public ResponseEntity<Set<Optional<LandDetails>>> getLandDetailsById(@RequestParam Long id)throws Exception{
        Set<Optional<LandDetails>> st=userService.getLandDetailsById(id);

            return ResponseEntity.ok(userService.getLandDetailsById(id));

//        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam Long id){
         userService.deleteUser(id);
         return ResponseEntity.ok("Deleted User");

    }

    @GetMapping("/getUserByOwnerWalletaddress")
    public ResponseEntity<Optional<User>> getUserByWalletAddress(@RequestParam String ownerWalletAddress){
        Optional<User> user=userRepository.findByownerWalletAddress(ownerWalletAddress);
        if(!user.isEmpty())return ResponseEntity.ok(user);
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));

    }


}
