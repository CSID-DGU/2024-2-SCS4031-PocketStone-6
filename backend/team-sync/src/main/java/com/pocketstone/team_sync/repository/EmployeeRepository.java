package com.pocketstone.team_sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketstone.team_sync.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
