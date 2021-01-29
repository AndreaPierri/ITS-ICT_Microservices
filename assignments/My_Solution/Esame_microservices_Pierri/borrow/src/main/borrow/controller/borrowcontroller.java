package org.pierri.borrow.controller;

import org.pierri.borrow.model.Borrow;
import org.pierri.borrow.repos.BorrowRepository;
import org.pierri.borrow.services.NotificationClient;
import org.pierri.borrow.services.TraceService;
//import org.example.borrow.exceptions.BorrowNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/v2/borrow")
public class BorrowController {

    @Autowired
    TraceService traceService;

    @Autowired
    NotificationClient notificationClient;

    @Value("${kafka.sms.message}")
    private String message;

    @Autowired
    private final BorrowRepository borrowRepository;

    public BorrowController(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    // CREATE A BORROWING
    @RequestMapping(method = RequestMethod.PUT)
    public Borrow addNewBorrow( @RequestBody Borrow borrow) {
        notificationClient.sendSMS(borrow);
        return borrowRepository.save(borrow);
    }


    // GET A SPECIFIC BORROWING
    @RequestMapping(value = "/{borrowId}", method = RequestMethod.GET)
    public Borrow getBorrow(@PathVariable Long borrowId) {
        Optional<Borrow> borrowOptional = borrowRepository.findById(borrowId);
        if(borrowOptional.isPresent()){
            return borrowOptional.get();
        }else{
            log.warn("STATEMENT: NO RESULT FOUND");
            return null;
        }
    }

    //GET ALL BORROWING
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Borrow> getAllBorrows() {
        log.info("Get all borrow order");
        List<Borrow> result = new ArrayList<Borrow>();
        Iterable<Borrow> iterable = borrowRepository.findAll();
        iterable.forEach(result::add);
        return result;
    }


    //UPDATE A BORROWING
    @RequestMapping(value = "/{borrowId}", method = RequestMethod.POST)
    public Borrow modifyBorrow(@RequestBody Borrow borrow, @RequestBody String borrowId ) {
        return borrowRepository.save(borrow);
    }


    //DELETE ALL BORROWING
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllBorrow() {
        borrowRepository.deleteAll();
        log.warn("I BECOME DEATH, DESTROYER OF WORLDS");
    }

    //DELETE A SPECIFIC BORROWING
    @RequestMapping(value = "/{borrowId}", method = RequestMethod.DELETE)
    public void deleteBorrow(@PathVariable Long borrowId) {
        borrowRepository.deleteById(borrowId);
        log.warn("One and done");
    }

}