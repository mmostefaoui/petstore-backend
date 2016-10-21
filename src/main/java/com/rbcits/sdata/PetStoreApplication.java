package com.rbcits.sdata;

import com.rbcits.sdata.domain.entities.AppUser;
import com.rbcits.sdata.domain.entities.Privilege;
import com.rbcits.sdata.domain.entities.Role;
import com.rbcits.sdata.domain.repository.AppUserRepository;
import com.rbcits.sdata.domain.repository.PrivilegeRepository;
import com.rbcits.sdata.domain.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootApplication
public class PetStoreApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PetStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner init(final AppUserRepository appUserRepository,
                           final RoleRepository roleRepository,
                           final PrivilegeRepository privilegeRepository,
                           final PasswordEncoder passwordEncoder) {

        return new CommandLineRunner() {

            @Override
            public void run(String... arg0) throws Exception {

                final Privilege unlimitedPrivilege = createPrivilegeIfNotFound("UNLIMITED_PRIVILEGE");

                //Add/find/view/delete)
                final Privilege addPrivilege = createPrivilegeIfNotFound("ADD_PRIVILEGE");
                final Privilege findPrivilege = createPrivilegeIfNotFound("FIND_PRIVILEGE");
                final Privilege viewPrivilege = createPrivilegeIfNotFound("VIEW_PRIVILEGE");
                final Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
                final Privilege updatePrivilege = createPrivilegeIfNotFound("UPDATE_PRIVILEGE");



                final List<Privilege> adminPrivileges = Arrays.asList(unlimitedPrivilege);
                createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
                createRoleIfNotFound("ROLE_USER_WRITE", Arrays.asList(addPrivilege, findPrivilege, viewPrivilege, deletePrivilege, updatePrivilege));
                createRoleIfNotFound("ROLE_USER_READ", Arrays.asList(findPrivilege, viewPrivilege));

                createUserIfNotFound("admin", "ROLE_ADMIN");
                createUserIfNotFound("userA", "ROLE_USER_READ");
                createUserIfNotFound("userB", "ROLE_USER_WRITE");
            }

            @Transactional
            private final Privilege createPrivilegeIfNotFound(final String name) {
                Privilege privilege = privilegeRepository.findByName(name);
                if (privilege == null) {
                    privilege = new Privilege(name);
                    privilegeRepository.save(privilege);
                }
                return privilege;
            }

            @Transactional
            private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
                Role role = roleRepository.findByName(name);
                if (role == null) {
                    role = new Role(name);
                    role.setPrivileges(privileges);
                    roleRepository.save(role);
                }
                return role;
            }

            @Transactional
            private final AppUser createUserIfNotFound(String username, String role) {
                AppUser user = appUserRepository.findByUsername(username);

                if (user == null) {
                    user = new AppUser();
                    user.setFirstName(username);
                    user.setLastName(username);
                    user.setUsername(username);
                    user.setPassword(passwordEncoder.encode("Passw0rd!"));
                    user.setEmail("test@test.com");
                    user.setRoles(Arrays.asList(roleRepository.findByName(role)));
                    user.setUserStatus(0);

                    appUserRepository.save(user);
                }
                return user;
            }

        };
    }
}
