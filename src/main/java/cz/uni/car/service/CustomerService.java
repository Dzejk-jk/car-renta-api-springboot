package cz.uni.car.service;

import cz.uni.car.dto.CustomerDTO;
import cz.uni.car.entity.CustomerEntity;
import cz.uni.car.mapper.CustomerMapper;
import cz.uni.car.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        CustomerEntity customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    public CustomerDTO getById(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Zákazník s id " + id + " nenalezen."));
        return customerMapper.toDto(customer);
    }

    public List<CustomerDTO> getAll() {
        List<CustomerEntity> entities = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (CustomerEntity entity : entities) {
            customerDTOS.add(customerMapper.toDto(entity));
        }
        return customerDTOS;
    }
}
