package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee extends User {
    public Employee(Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    @ElementCollection
    @JoinTable(name="employee_availability")
    private Set<DayOfWeek> daysAvailable;

    @ElementCollection
    @JoinTable(name="employee_skills")
    private Set<EmployeeSkill> skills;
}
