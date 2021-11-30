package com.example.adverts.repository.user;

import com.example.adverts.model.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

}
