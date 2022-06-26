package com.qlik.task;

import com.qlik.task.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PalindromeService {

    private final PalindromeRepository palindromeRepository;

    public PalindromeService(PalindromeRepository palindromeRepository) {
        this.palindromeRepository = palindromeRepository;
    }

    /**
     * Find all palindromes from the database
     */
    public List<Palindrome> findAll() {
        return palindromeRepository.findAll();
    }

    public Palindrome add(String text) throws ApiException {
        Optional<Palindrome> palindromeByText = palindromeRepository.findByText(text);
        if (palindromeByText.isPresent()) {
            throw new ApiException(text + "  is already existed");
        }

        Palindrome palindrome = new Palindrome(text);
        palindromeRepository.save(palindrome);

        return palindrome;
    }

    public Palindrome findById(Long id) throws ApiException {
        Optional<Palindrome> exist = palindromeRepository.findById(id);
        if (!exist.isPresent()) {
            throw new ApiException(id + " does not existed");
        }

        return exist.get();
    }

    public Palindrome findByText(String text) throws ApiException {
        Optional<Palindrome> exist = palindromeRepository.findByText(text);
        if (!exist.isPresent()) {
            throw new ApiException(text + " does not existed");
        }

        return exist.get();
    }

    public List<Palindrome> findAllByIsPalindromeFalse() {
        return palindromeRepository.findAlByIsPalindromeFalse();
    }

    public List<Palindrome> findAllByIsPalindromeTrue() {
        return palindromeRepository.findAllByIsPalindromeTrue();
    }

    public List<Palindrome> findByTextContaining(String text) {
        return palindromeRepository.findByTextContaining(text);
    }

    public void deleteAll() {
        palindromeRepository.deleteAll();
    }

    public Palindrome deleteById(Long id) throws ApiException {
        Optional<Palindrome> exist = palindromeRepository.findById(id);
        if (!exist.isPresent()) {
            throw new ApiException(id + " does not existed");
        }

        palindromeRepository.deleteById(id);
        return exist.get();
    }

}
