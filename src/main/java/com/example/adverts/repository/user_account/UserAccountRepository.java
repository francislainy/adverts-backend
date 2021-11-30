package com.example.adverts.repository.user_account;

import com.example.adverts.model.entity.userinfo.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, UUID> {

}
