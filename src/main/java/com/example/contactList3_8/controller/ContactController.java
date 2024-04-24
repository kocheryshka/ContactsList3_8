package com.example.contactList3_8.controller;

import com.example.contactList3_8.data.Contact;
import com.example.contactList3_8.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/")
    public String index(Model model){
        log.debug("ContactController->index");
        model.addAttribute("contacts", contactService.findAll());
        return "index";
    }

    @GetMapping("/contact/edit")
    public String showCreateForm(Model model) {
        log.debug("ContactController->showCreateForm");
        model.addAttribute("contact", new Contact());
        model.addAttribute("action", "Создать контакт");
        return "edit";
    }

    @PostMapping("/contact/edit")
    public String save(@ModelAttribute Contact contact){
        log.debug("ContactController->save" + contact);
        contactService.save(contact);
        return "redirect:/";
    }

    @GetMapping("/contact/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        log.debug("ContactController->showEditForm");
        Contact contact = contactService.findById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            model.addAttribute("action", "Редактировать контакт");
            return "edit";
        }
        return "redirect:/";
    }

    @GetMapping("/contact/delete/{id}")
    public String delete(@PathVariable Long id){
        log.debug("ContactController->delete id={}", id);
        contactService.delete(id);
        return "redirect:/";
    }

}
