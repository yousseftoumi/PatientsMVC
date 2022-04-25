package ma.emsi.hospital.security.service;

import ma.emsi.hospital.security.entities.AppRole;
import ma.emsi.hospital.security.entities.AppUser;

public interface SecurityService {
    AppUser saveNewUser(String username,String password,String passwordConfirmation);
    AppRole saveNewRole(String roleName,String description);
    void addRoleToUser(String username,String roleName);
    void removeRoleFromUser(String username,String roleName);
    AppUser loadUserByUsername(String username);

}
