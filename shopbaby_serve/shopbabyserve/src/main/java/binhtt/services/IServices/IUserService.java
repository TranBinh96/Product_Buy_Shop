package binhtt.services.IServices;

import binhtt.dtos.UsersDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.User;

public interface IUserService {
        User createUser(UsersDTO userdto) throws DataNotFoundException;
        String login(String phone, String password) throws Exception;
}
