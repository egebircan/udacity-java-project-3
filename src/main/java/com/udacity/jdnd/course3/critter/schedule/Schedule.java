package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeSkill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;

    @ElementCollection
    @JoinTable(name="schedule_activities")
    private Set<EmployeeSkill> activities;

    @ManyToMany
    @JoinTable(
            name="schedule_employee",
            joinColumns = @JoinColumn(name="schedule_id"),
            inverseJoinColumns = @JoinColumn(name="employee_id")
    )
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(
            name="schedule_pet",
            joinColumns = @JoinColumn(name="schedule_id"),
            inverseJoinColumns = @JoinColumn(name="pet_id")
    )
    private List<Pet> pets;
}
