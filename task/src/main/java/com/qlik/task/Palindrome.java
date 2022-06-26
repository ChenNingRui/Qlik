package com.qlik.task;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Table(name = "palindrome")
public class Palindrome {
    @Id
    @SequenceGenerator(
            name = "palindrome_sequence",
            sequenceName = "palindrome_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "palindrome_sequence"
    )

    private Long id;
    private String text;
    private boolean isPalindrome;
    private Date date;

    public Palindrome() {
    }

    public Palindrome(String text) {
        this.text = text;
        this.date = new Date();
        this.isPalindrome = checkPalindrome(text);
    }

    public boolean checkPalindrome(String str) {
        if (str == null || str.length() < 1) return false;

        int l = 0, r = str.length() - 1;
        while (l <= r) {
            if (str.charAt(l) != str.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Palindrome{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isPalindrome=" + isPalindrome +
                ", date=" + date +
                '}';
    }
}
