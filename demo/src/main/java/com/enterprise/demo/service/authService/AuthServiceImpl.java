package com.enterprise.demo.service.authService;

import com.enterprise.demo.dto.customer.CustomerRequestDto;
import com.enterprise.demo.dto.customer.CustomerResponse;
import com.enterprise.demo.dto.otp.OtpRequest;
import com.enterprise.demo.dto.auth.AuthRequest;
import com.enterprise.demo.dto.auth.AuthResponse;
import com.enterprise.demo.dto.forgotpassword.ForgotPasswordRequest;
import com.enterprise.demo.dto.forgotpassword.ForgotPasswordResponse;
import com.enterprise.demo.entity.Cart;
import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.entity.Role;
import com.enterprise.demo.mapper.CustomerMapper;
import com.enterprise.demo.repository.CustomerRepository;
import com.enterprise.demo.service.EmailService;
import com.enterprise.demo.service.OtpService;
import com.enterprise.demo.utills.CustomUserDetailsService;
import com.enterprise.demo.utills.CustomUserPrincipal;
import com.enterprise.demo.utills.JwtUtills;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtills jwtUtills;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final EmailService emailService;

    private HashMap<String,String> otpStorage= new HashMap<>();
    @Override
    public CustomerResponse register(CustomerRequestDto customerRequestDto) {

        Customer customer = CustomerMapper.toEntity(customerRequestDto);
        customer.setRole(Role.CUSTOMER);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);
        customerRepository.save(customer);

        return CustomerMapper.toDto(customer);
    }
    @Override
    public String login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String otp = otpService.generateOtp();
        otpStorage.put(request.getEmail(), otp);
        emailService.sendOtp(request.getEmail(), otp);
        return "Email Sent Successfully";

    }

    public AuthResponse verifyOtp(OtpRequest request){
        String storedOtp = otpStorage.get(request.getEmail());
        if(storedOtp==null ||!storedOtp.equals(request.getOtp())){
            throw new RuntimeException("Invalid Otp");
        }
        otpStorage.remove(request.getEmail());
        CustomUserPrincipal principal = (CustomUserPrincipal) userDetailsService.loadUserByUsername(request.getEmail());
        Customer customer  = principal.getCustomer();
        String token= jwtUtills.generateToken(customer);
        return AuthResponse.builder()
                .email(principal.getUsername())
                .role(customer.getRole().name())
                .token(token)
                .build();
    }

    @Override
    public ForgotPasswordResponse forgetPassword(ForgotPasswordRequest request) {
        Customer existingCustomer = customerRepository.findByEmail(request.getEmail());
        if(existingCustomer==null){
            throw new RuntimeException("User Not Found");
        }
          if(passwordEncoder.matches(request.getNewPassword(), existingCustomer.getPassword())){
            throw new IllegalArgumentException
                    ("Both Old and new password is same u should select new password");
        }
        request.setNewPassword(passwordEncoder.encode(request.getNewPassword()));


        existingCustomer.setPassword(request.getNewPassword());
        customerRepository.save(existingCustomer);
        return ForgotPasswordResponse.builder()
                .newPassword(existingCustomer.getPassword())
                .build();

    }


}
