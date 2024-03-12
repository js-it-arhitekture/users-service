package com.example.nakup_vstopnic.repository;

import com.example.nakup_vstopnic.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


// Repository sam generira osnovne CRUD metode, zraven se lahko poljubne mi napisemo
@Repository
public interface UserRepository extends MongoRepository<User,String> {

    //
}
