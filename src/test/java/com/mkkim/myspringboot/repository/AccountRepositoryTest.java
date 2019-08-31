package com.mkkim.myspringboot.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.mkkim.myspringboot.entity.Account;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRepositoryTest {
	@Autowired
	AccountRepository accountRepository;
	
	@Test
	public void account() throws Exception {
		accountRepository.findAll(new Sort(Direction.DESC, "id"));
		Account account = new Account();
		account.setUsername("spring");
		account.setPassword("spring");
		Account newAcct = accountRepository.save(account);
		System.out.println(newAcct.getId() + " " + newAcct.getUsername());
		assertThat(newAcct).isNotNull();
		
		Optional<Account> existAcct = accountRepository.findByUsername(newAcct.getUsername());
		assertThat(existAcct).isNotNull();
		Optional<Account> noExistAcct = accountRepository.findByUsername("test");
		assertThat(noExistAcct).isNull();
	}
	
	@Test
	public void optional() throws Exception{
		Optional<Account> optional = accountRepository.findByUsername("spring");
		if(optional.isPresent()) {
			Account account = optional.get();
			
			System.out.println(account.equals(new Account()));
		}
		optional.ifPresent(acct -> System.out.println(acct.toString()));
		optional.ifPresent(System.out::println);
		
		Optional<Account> notOpt = accountRepository.findByUsername("test");
		System.out.println("=================");
		notOpt.ifPresent(System.out::println);
		Account notAccount = notOpt.orElseThrow(() -> new Exception("username not found"));
		System.out.println("=================");
	}
}
