package com.protify.Protify.service;

import com.protify.Protify.models.User;
import com.protify.Protify.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

@NonNull
    private final UserRepository userRepository;


    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return userRepository.saveAll(entities);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }
}

