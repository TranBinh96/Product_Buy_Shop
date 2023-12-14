package binhtt.services;

import binhtt.dtos.UsersDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Role;
import binhtt.models.User;
import binhtt.respository.RoleReponsitory;
import binhtt.respository.UserReponsitory;
import binhtt.services.IServices.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private  final UserReponsitory userReponsitory;
    private final RoleReponsitory roleReponsitory;

    @Override
    public User createUser(UsersDTO userdto) throws DataNotFoundException {
        if (userReponsitory.existsByPhoneNumber(userdto.getPhoneNumber())){
            throw  new DataIntegrityViolationException("Phone Number already exits");
        }
        User user = User
                .builder()
                .fullname(userdto.getFullName())
                .dateOfBirth(userdto.getDateOfBirthday())
                .address(userdto.getAddress())
                .facebookAccountId(userdto.getFacebookAccountId())
                .googleAccountId(userdto.getGoogleAccountId())
                .build();

        Role role = roleReponsitory.findById(userdto.getRoleId())
                .orElseThrow(()-> new DataNotFoundException("Role Not Found") );
        user.setRole(role);

        if (userdto.getGoogleAccountId() == 0 && userdto.getFacebookAccountId()==0){
            user.setPassword(userdto.getPassword());
        }
        return userReponsitory.save(user);
    }

    @Override
    public String login(String phone, String password) {
        return null;
    }
}
