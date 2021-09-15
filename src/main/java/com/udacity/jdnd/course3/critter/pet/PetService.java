package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public Pet savePet(Pet newPet, Long ownerId) {
        Customer owner = customerRepository.getOne(ownerId);

        newPet.setCustomer(owner);

        petRepository.save(newPet);

        owner.getPets().add(newPet);
        customerRepository.save(owner);

        return newPet;
    }

    public Pet findPetById(Long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> findPetsByOwnerId(Long ownerId) {
        Customer owner = customerRepository.getOne(ownerId);

        return owner.getPets();
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }
}
