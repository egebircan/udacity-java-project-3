package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findOwnerByPetId(Long petId) {
        Pet pet = petRepository.getOne(petId);

        return pet.getCustomer();
    }

    public Customer saveCustomer(Customer newCustomer, List<Long> petIds) {
        List<Pet> petList = new ArrayList<>();

        if (petIds != null) {
            petList = petIds.stream().map(petRepository::getOne).collect(toList());
        }
        newCustomer.setPets(petList);

        return customerRepository.save(newCustomer);
    }
}
