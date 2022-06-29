package com.qlik.task;

import com.qlik.task.dto.PalindromeDto;
import com.qlik.task.exception.ApiException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.qlik.task.Util.convertToDto;

@RestController
@RequestMapping(path = "api/v1/palindrome")
public class PalindromeController {

    private final PalindromeService palindromeService;

    @Autowired
    public PalindromeController(PalindromeService palindromeService) {
        this.palindromeService = palindromeService;
    }

    @ApiOperation(value = "Get all Palindromes from DB", notes = "Return a list of Palindromes")
    @GetMapping("/all")
    public List<PalindromeDto> findAll() {
        return convertToDto(palindromeService.findAll());
    }

    @ApiOperation(value = "Add a Palindrome entity to DB", notes = "Return a Palindromes")
    @PostMapping("/add/{text}")
    public PalindromeDto add(@PathVariable String text) throws ApiException {
        Palindrome rv = palindromeService.add(text);
        return new PalindromeDto(rv.getId(), rv.getText(), rv.isPalindrome(), rv.getDate());
    }

    @ApiOperation(value = "Find a Palindrome entity based on the ID", notes = "Return a Palindromes, text is an unique value in DB")
    @PostMapping("/findById/{palindromeId}")
    public PalindromeDto findById(@PathVariable Long palindromeId) throws ApiException {
        Palindrome rv = palindromeService.findById(palindromeId);
        return new PalindromeDto(rv.getId(), rv.getText(), rv.isPalindrome(), rv.getDate());
    }

    @ApiOperation(value = "Find a Palindrome entity based on the test", notes = "Return a Palindromes, text is an unique value in DB")
    @PostMapping("/findByText{text}")
    public PalindromeDto findByText(@PathVariable String text) throws ApiException {
        Palindrome rv = palindromeService.findByText(text);
        return new PalindromeDto(rv.getId(), rv.getText(), rv.isPalindrome(), rv.getDate());
    }

    @ApiOperation(value = "Find all Palindromes from DB based on the isPalindrome value", notes = "Return a Palindromes list")
    @PostMapping("/findAllByIsPalindrome")
    public List<PalindromeDto> findAllByIsPalindrome(@RequestBody boolean isPalindrome) {
        if (isPalindrome) {
            return convertToDto(palindromeService.findAllByIsPalindromeTrue());
        } else {
            return convertToDto(palindromeService.findAllByIsPalindromeFalse());
        }
    }

    @ApiOperation(value = "Find all Palindromes from DB if the Palindromes contain the input string", notes = "Return a Palindromes list")
    @PostMapping("/findByTextContaining")
    public List<PalindromeDto> findByTextContaining(@RequestBody String text) {
        List<Palindrome> palindromes = palindromeService.findByTextContaining(text);
        return convertToDto(palindromes);
    }

    @ApiOperation(value = "Delete the Palindrome based on the id", notes = "Return HttpStatus")
    @DeleteMapping("/deleteById/{id}")
    public HttpStatus deleteById(@PathVariable Long id) throws ApiException {
        palindromeService.deleteById(id);
        return HttpStatus.OK;
    }

}
