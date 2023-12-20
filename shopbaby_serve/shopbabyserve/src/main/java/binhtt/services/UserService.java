package binhtt.services;

import binhtt.components.JwtTokenUtil;
import binhtt.dtos.UsersDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Role;
import binhtt.models.User;
import binhtt.respository.RoleReponsitory;
import binhtt.respository.UserReponsitory;
import binhtt.services.IServices.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private  final UserReponsitory userReponsitory;
    private final RoleReponsitory roleReponsitory;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtTokenUtil jwtTokenUtil;

    private AuthenticationManager authenticationManager;

    @Override
    public User createUser(UsersDTO userdto) throws DataNotFoundException {
        if (userReponsitory.existsByPhoneNumber(userdto.getPhoneNumber())){
            throw  new DataIntegrityViolationException("Phone Number already exits");
        }
        Role role = roleReponsitory.findById(userdto.getRoleId())
                .orElseThrow(()-> new DataNotFoundException("Role Not Found") );
        if (role.getName().toUpperCase().equals(Role.ADMIN)){
            throw  new DataNotFoundException("You can create user with role ID : "+Role.ADMIN);
        }
        User user = User.fromUser(userdto,role);

        if (userdto.getGoogleAccountId() == 0 && userdto.getFacebookAccountId()==0){
            user.setPassword(passwordEncoder.encode(userdto.getPassword()));
        }

        return userReponsitory.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {

            Optional<User> existsUser =  userReponsitory.findByPhoneNumber(phoneNumber);
            if (existsUser.isEmpty()){
                throw  new DataNotFoundException("Found not user with phone number"+phoneNumber);
            }
            //check password

            if (existsUser.get().getGoogleAccountId() == 0 && existsUser.get().getFacebookAccountId()==0){
                if (!passwordEncoder.matches(password,existsUser.get().getPassword())){
                    throw  new BadCredentialsException("Wrong phone number / password");
                }
            }

            UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken(
                    phoneNumber,password
            );
            authenticationManager.authenticate(authenticationToken);
            //Authenticate with Java spring
            return jwtTokenUtil.generateToken(existsUser.get());

    }

}
