package com.example.BillingApp.Controller;

import com.example.BillingApp.Entity.Invoice;
import com.example.BillingApp.Service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return service.saveInvoice(invoice);
    }

    @GetMapping
    public List<Invoice> getInvoices() {
        return service.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getInvoice(@PathVariable Long id) {
        return service.getInvoice(id);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        service.deleteInvoice(id);
    }
}
