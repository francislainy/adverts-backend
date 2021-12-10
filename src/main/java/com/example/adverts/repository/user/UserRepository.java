package com.example.adverts.repository.user;

import com.example.adverts.model.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

//    String queryByUsername = "SELECT * FROM answer INNER JOIN question on answer.question_id = question.id WHERE answer.question_id = :id ORDER BY answer.creation_date DESC";
    String queryByUsername = "SELECT * FROM user_account WHERE user_account.username = :username";

    @Query(value = queryByUsername, nativeQuery = true)
    User findUserByUsername(String username);
}
