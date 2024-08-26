package com.example.controller;

import com.example.model.Item;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.ItemService;


@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "cart";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                item.setImage(imageFile.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemService.saveItem(item);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateItem(@ModelAttribute Item item, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                item.setImage(imageFile.getBytes());
            } else {
                Item existingItem = itemService.getItemById(item.getId());
                if (existingItem != null) {
                    item.setImage(existingItem.getImage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemService.saveItem(item);
        return "redirect:/cart";
    }
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/cart";
    }
}


