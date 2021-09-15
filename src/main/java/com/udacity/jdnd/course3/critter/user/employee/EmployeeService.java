package com.udacity.jdnd.course3.critter.user.employee;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findEmployeeById(Long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public List<Employee> findEmployeesWithMatchingSkillsAndDate(Set<EmployeeSkill> skills, LocalDate date) {
        return employeeRepository
                .findAllByDaysAvailableContaining(date.getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void setDayAvailableByEmployeeId(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }
}
