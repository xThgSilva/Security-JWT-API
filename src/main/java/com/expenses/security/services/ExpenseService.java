package com.expenses.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses.security.dto.ExpenseDTO;
import com.expenses.security.entities.Expense;
import com.expenses.security.entities.User;
import com.expenses.security.repositories.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Get
    public List<Expense> listByUser(Long usuarioId) {
        return expenseRepository.findByUserId(usuarioId);
    }

    // Post
    public Expense saveExpense(ExpenseDTO dto, Long usuarioId) {
    	// Salvando o Id do usuário para associar a Expense
        User user = new User();
        user.setId(usuarioId);
        
        // Salvando os dados da Expense
        Expense expense = new Expense();
        expense.setTitle(dto.getTitle());
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        
        //Salvando o Id do usuário que foi obtido pela extração do Token
        expense.setUser(user);
        
        return expenseRepository.save(expense);
    }

    // Delete
    public void deleteExpense(Long id) {
    	expenseRepository.deleteById(id);
    }

    // Update
    public Expense updateExpense(Long id, Expense expenseUpdated, Long userId) {
    	Expense current = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found."));

        if (!current.getUser().getId().equals(userId)) {
            throw new RuntimeException("User not authorized to change this expense.");
        }

        current.setTitle(expenseUpdated.getTitle());
        current.setDescription(expenseUpdated.getDescription());
        current.setAmount(expenseUpdated.getAmount());
        return expenseRepository.save(current);
    }
}
