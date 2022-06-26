package com.qlik.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PalindromeRepository extends JpaRepository<Palindrome, Long> {

    @Query
    Optional<Palindrome> findByText(String text);

    @Query
    List<Palindrome> findByTextContaining(String text);

    @Query
    List<Palindrome> findAllByIsPalindromeTrue();

    @Query
    List<Palindrome> findAlByIsPalindromeFalse();


}
