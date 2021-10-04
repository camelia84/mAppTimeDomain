package com.mAppTime.mAppTimeDomain.dao;

import com.mAppTime.mAppTimeDomain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

}
