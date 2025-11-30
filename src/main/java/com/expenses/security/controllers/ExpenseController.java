package com.expenses.security.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.security.dto.ExpenseDTO;
import com.expenses.security.entities.Expense;
import com.expenses.security.repositories.ExpenseRepository;
import com.expenses.security.services.ExpenseService;
import com.expenses.security.services.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private JwtService jwtService;

    private Long userToken(HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("jwt"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        return jwtService.getUserIdFromToken(token);
    }

    @GetMapping(value = "/list")
    public List<Expense> listByUser(HttpServletRequest request) {
        Long usuarioId = userToken(request);
        return expenseService.listByUser(usuarioId);
    }

    @PostMapping(value = "/register")
    public Expense register(@RequestBody ExpenseDTO dto, HttpServletRequest request) {
        Long userId = userToken(request);
        return expenseService.saveExpense(dto, userId);
    }

    @PutMapping(value = "/update/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense expense, HttpServletRequest request) {
        Long userId = userToken(request);
        return expenseService.updateExpense(id, expense, userId);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id, HttpServletRequest request) {
        expenseService.deleteExpense(id);
    }
}
