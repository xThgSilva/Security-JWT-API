package com.expenses.security.dto;

import com.expenses.security.entities.Expense;

public class ExpenseDTO {
	private Long id;
	private String title;
	private String description;
	private float amount;
	private Long userId;
	
	public ExpenseDTO() {}

	public ExpenseDTO(Long id, String title, String description, float amount, Long userId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.amount = amount;
		this.userId = userId;
	}

	public ExpenseDTO(Expense entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.description = entity.getDescription();
		this.amount = entity.getAmount();
		this.userId = entity.getUser().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
