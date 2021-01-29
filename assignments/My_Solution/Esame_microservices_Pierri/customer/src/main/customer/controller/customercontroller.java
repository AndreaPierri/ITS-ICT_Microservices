package org.pierri.customer.controller;

import org.pierri.customer.repos.CustomerRepository;
import org.pierri.customer.model.Customer;
import org.pierri.customer.services.TraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = "/v2/customer")
public class CustomerController {
    @Autowired
    TraceService traceService;

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CREATE A NEW CUSTOMER
    @RequestMapping(method = RequestMethod.PUT)
    public Customer addNewCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }


    // GET A SPECIFIC CUSTOMER
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable String customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
            return customerOptional.get();
        }else{
            log.warn("STATEMENT: NO CUSTOMER FOUND");
            return null;
        }
    }

    // GET ALL THE CUSTOMER
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Customer> getAllCustomers() {
        log.info("Get all customers");
        return customerRepository.findAll();
    }


    // UPDATE A CUSTOMER
    @RequestMapping(value = "/{customerId}", method = RequestMethod.POST)
    public Customer modifyCustomer(@RequestBody Customer customer, @RequestBody String customerId ) {
        return customerRepository.save(customer);
    }


    // DELETE ALL CUSTOMER
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
        log.warn("You are like a delightful random cruelty generator");
    }

    //DELETE A SPECIFIC CUSTOMER
    @RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable String customerId) {
        customerRepository.deleteById(customerId);
        log.warn("Meatbag eliminated");
    }
}