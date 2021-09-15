package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    public ScheduleController(ScheduleService scheduleService, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.scheduleService = scheduleService;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        List<Employee> employees = employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        List<Pet> pets = petRepository.findAllById(scheduleDTO.getPetIds());
        schedule.setPets(pets);

        return convertToDTO(scheduleService.saveSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAllSchedules();
        return schedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> petSchedules = scheduleService.findSchedulesByPetId(petId);
        return petSchedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> employeeSchedules = scheduleService.findSchedulesByEmployeeId(employeeId);
        return employeeSchedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> ownerSchedules = scheduleService.findScheduleByOwnerId(customerId);
        return ownerSchedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));

        return scheduleDTO;
    }
}
