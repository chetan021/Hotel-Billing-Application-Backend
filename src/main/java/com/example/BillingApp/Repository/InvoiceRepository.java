package com.example.BillingApp.Repository;

import com.example.BillingApp.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> { }

