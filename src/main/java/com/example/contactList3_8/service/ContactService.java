package com.example.contactList3_8.service;

import com.example.contactList3_8.data.Contact;
import com.example.contactList3_8.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repository;

    public List<Contact> findAll(){
        log.debug("ContactService->findAll");
        return repository.findAll();
    }

    public Contact findById(Long id){
        log.debug("ContactService->findById id={}", id);
        return repository.findById(id).orElse(null);
    }

    public void save(Contact contact) {
        log.debug("ContactService->save {}", contact);
        if (contact.getId() == null) {
            repository.save(contact);
        } else{
            if (findById(contact.getId()) != null) {
                repository.update(contact);
            }
        }
    }

    public void delete(Long id) {
        log.debug("ContactService->save id = {}", id);
        if (findById(id) != null) {
            repository.delete(id);
        }
    }
}
