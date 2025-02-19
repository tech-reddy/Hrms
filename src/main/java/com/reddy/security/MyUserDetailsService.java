//package com.reddy.security;
//
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@AllArgsConstructor
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    private final UserRepo repo;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user= repo.findByUsername(username);
//
//        if (user==null) {
//            System.out.println("User 404");
//            throw new UsernameNotFoundException("User 404");
//        }
//        return new UserPrincipal(user);
//    }
//
//}
////public class EmployeeDetailsService  implements UserDetailsService {
////    private final EmployeeRepository employeeRepository;
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        Employee employee = employeeRepository.findByUsername(username);
////        GrantedAuthority authorities = new SimpleGrantedAuthority( employee.getRole().getName());
////
////        return new User(
////                username,
////                employee.getPassword(),
////                (Collection<? extends GrantedAuthority>) authorities
////        );
////    }
////}
