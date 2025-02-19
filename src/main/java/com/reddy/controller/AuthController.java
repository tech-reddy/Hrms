//package com.reddy.controller;
//
//import com.reddy.dto.employee.EmployeeRequestDTO;
//import com.reddy.model.employee.Employee;
//import com.reddy.repository.employee.EmployeeRepository;
//import com.reddy.security.JwtUtil;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/auth")
//@AllArgsConstructor
//public class AuthController {
//
//    private final EmployeeRepository userRepository;
//
//    private final JwtUtil jwtUtil;
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    // Register API
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody EmployeeRequestDTO request) {
//        Optional<Employee> existingUser = userRepository.findByUsername(request.getUsername());
//        if (existingUser.isPresent()) {
//            return ResponseEntity.badRequest().body("Username already exists!");
//        }
//
//        Employee user = new Employee();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole(request.getRole());
//
//        userRepository.save(user);
//        return ResponseEntity.ok("User registered successfully!");
//    }
//
//    // Login API
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody EmployeeRequestDTO request) {
//        Optional<Employee> userOpt = userRepository.findByUsername(request.getUsername());
//
//        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
//            return ResponseEntity.badRequest().body("Invalid credentials");
//        }
//
//        String token = jwtUtil.generateToken(request.getUsername());
//        return ResponseEntity.ok(token);
//    }
//}
