package com.example.schedulemanageapp.domain.users.repository;

import com.example.schedulemanageapp.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
