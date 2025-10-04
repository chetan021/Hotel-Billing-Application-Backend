package com.example.BillingApp.Service;

import com.example.BillingApp.Entity.Invoice;
import com.example.BillingApp.Repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository repo;

    public InvoiceService(InvoiceRepository repo) {
        this.repo = repo;
    }

    public Invoice saveInvoice(Invoice invoice) {
        return repo.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return repo.findAll();
    }

    public Invoice getInvoice(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public void deleteInvoice(Long id) {
        repo.deleteById(id);
    }
}

