package ru.practicum.explorewithme.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.users.model.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> getUsersByIdIsIn(Set<Long> usersId, Pageable pageable);
}
