package com.mkkim.myspringboot.service;

import com.mkkim.myspringboot.entity.Account;
import com.mkkim.myspringboot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @PostConstruct
    public void init(){
        Optional<Account> opt = accountRepository.findByUsername("shlee2");
        if(!opt.isPresent()){
            Account newAccount = this.createAccount("shlee2", "1234");
            System.out.println(newAccount);
        }
    }

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //Account 레코드 추가
    public Account createAccount(String username, String password){
        Account account = new Account();
        account.setUsername(username);
        //account.setPassword(password);
        account.setPassword(passwordEncoder.encode(password));
        return accountRepository.save(account);
    }


    @Override
    // 로그인 할때 사용자가 입력한 정보가 유효한지를 체크한다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> opt = accountRepository.findByUsername(username);
        Account account = opt.orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(account.getUsername(), account.getPassword(), authrities());
    }

    // User 객체의 세번째 인자 USER라는 ROLE을 가진 사용자이다 라고 설정하는 부분
    private Collection<? extends GrantedAuthority> authrities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_UESR"));
    }
}
