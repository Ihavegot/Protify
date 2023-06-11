package com.protify.Protify.service;

import com.protify.Protify.Exceptions.ResourceNotFoundException;
import com.protify.Protify.models.User;
import com.protify.Protify.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User getSingle(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user with id " + id));
    }

    public Optional<User> findByLogin(String login) {
        return  userRepository.findByLogin(login);
    }
}

