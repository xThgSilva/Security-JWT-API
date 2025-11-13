package com.expenses.security.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.expenses.security.entities.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Long>{
	List <Expense> findByUserId(Long userId);
}
