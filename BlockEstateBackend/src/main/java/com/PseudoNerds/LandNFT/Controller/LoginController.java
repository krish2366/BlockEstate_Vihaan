package com.PseudoNerds.LandNFT.Controller;

import com.PseudoNerds.LandNFT.Dto.LoginRequest;
import com.PseudoNerds.LandNFT.Entity.User;
import com.PseudoNerds.LandNFT.Repository.UserRepository;
import com.PseudoNerds.LandNFT.Service.UserService;
import com.PseudoNerds.LandNFT.Util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;



@RestController
@RequestMapping("/User")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    Logger log=LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest authRequest)throws Exception{
        String username= authRequest.getUsername();
        String password= authRequest.getPassword();
        String token= jwtUtil.generateToken(username,password);
//        Optional<User> user=userRepository.getUserByusername(username);
//        User user2=user.get();
//        User user1=new User(user2.getId(),user2.getUsername(),user2.getOwnerWalletAddress(),user2.getPassword(),user2.getRoles());
//        user1.setPassword(null);
//        log.info(token);
        if(jwtUtil.isTokenValid(token))return ResponseEntity.ok(token);
        else throw new Exception("Token Invalid");
//        if(!user.isEmpty()){
//            return ResponseEntity.ok(user);
//        }
//        return new ResponseEntity<>(HttpStatusCode.valueOf(402));


    }

}
