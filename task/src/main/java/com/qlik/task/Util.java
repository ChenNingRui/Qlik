package com.qlik.task;

import com.qlik.task.dto.PalindromeDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static PalindromeDto convertToDto(Palindrome p) {
        return new PalindromeDto(p.getId(), p.getText(), p.isPalindrome(), p.getDate());
    }

    public static List<PalindromeDto> convertToDto(List<Palindrome> palindromeList) {
        return palindromeList.stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }
}
