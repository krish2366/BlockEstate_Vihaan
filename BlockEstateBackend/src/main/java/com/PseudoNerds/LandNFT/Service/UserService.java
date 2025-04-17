package com.PseudoNerds.LandNFT.Service;

import com.PseudoNerds.LandNFT.Entity.LandDetails;
import com.PseudoNerds.LandNFT.Entity.User;
import com.PseudoNerds.LandNFT.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LandDetailsService landDetailsService;

    public void registerUser(User user){
//        String pass=user.getPassword();
//        if (user.getPassword() == null || user.getPassword().isEmpty()) {
//            throw new IllegalArgumentException("Password must not be null or empty");
//        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    public Optional<User> getUserById(Long id){
        Optional<User> user=userRepository.findById(id);
        return user;
    }

    public Set<Optional<LandDetails>> getLandDetailsById(Long id)throws Exception {
        Optional<User> user=userRepository.findById(id);
        User user1=user.get();
        String ownerWalletAddress= user1.getOwnerWalletAddress();
        Set<Optional<LandDetails>> landDetails=landDetailsService.getLandDetailsByownerWalletAddress(ownerWalletAddress);
        return landDetails;


    }

    public User updateUser(User user){
        User updatedUser=new User();
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRoles(user.getRoles());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setOwnerWalletAddress(user.getOwnerWalletAddress());
        return updatedUser;
    }

    public void deleteUser(Long id){
        Optional<User> user=userRepository.findById(id);
        User user1=user.get();
        userRepository.delete(user1);
    }

    public Set<Optional<User>> getUserByownerWalletAddress(String ownerWalletAddress) {
        Set<Optional<User>> user=userRepository.getUserByownerWalletAddress(ownerWalletAddress);
        return user;
    }
}
