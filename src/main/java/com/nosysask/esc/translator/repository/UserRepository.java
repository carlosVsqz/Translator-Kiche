package com.nosysask.esc.translator.repository;

import com.nosysask.esc.translator.model.UserDto;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserDto, Long> {
    UserDto findByEmail(String email);
    UserDto findByUserId(String userId);
}
