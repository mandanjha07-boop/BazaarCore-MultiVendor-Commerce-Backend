    package com.enterprise.demo.utills;

    import com.enterprise.demo.entity.Customer;
    import com.enterprise.demo.repository.CustomerRepository;
    import lombok.*;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class CustomUserDetailsService implements UserDetailsService {
        private final CustomerRepository customerRepository;
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Customer customer = customerRepository.findByEmail(username);

            if(customer==null){
                throw new UsernameNotFoundException("User not found");
            }
            return new CustomUserPrincipal(customer);
    //        return User.builder()
    //                .username(customer.getEmail())
    //                .password(customer.getPassword())
    //                .roles(customer.getRole().name())
    //                .build();
        }
    }
