package com.example.contactList3_8.repository;

import com.example.contactList3_8.data.Contact;
import com.example.contactList3_8.repository.mapper.ContactRowMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Contact> findAll(){
        log.debug("ContactRepository->findAll");
        String sql = "select * from contact";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    public Optional<Contact> findById(Long id){
        log.debug("ContactRepository->findById, id = {}", id);
        String sql = "select * from contact where id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql,
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper()))
        );
        return Optional.ofNullable(contact);
    }

    public void save(Contact contact) {
        log.debug("ContactRepository->save, contact = {}", contact);
        contact.setId(System.currentTimeMillis());
        String sql = "insert into contact(id, first_name, last_name, email, phone) " +
                "values(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone());
    }

    public void update(Contact contact) {
        log.debug("ContactRepository->update, contact = {}", contact);
        String sql = "update contact set first_name = ?, last_name = ?, email = ?, phone = ? where id = ?";
        jdbcTemplate.update(sql,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getId());
    }

    public void delete(Long id) {
        log.debug("ContactRepository->delete, id = {}", id);
        String sql = "delete from contact where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
