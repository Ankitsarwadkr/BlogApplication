package com.example.BlogApplication.Config;

import com.example.BlogApplication.Entity.Category;
import com.example.BlogApplication.Repositry.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        String[] fixedCategories = {
                "Technology",
                "Travel",
                "Food",
                "Lifestyle",
                "Education",
                "Health & Fitness",
                "Business",
                "Uncategorized"
        };

        for (String name : fixedCategories) {
            categoryRepository.findByName(name).orElseGet(() -> {
                Category category = new Category(name);
                return categoryRepository.save(category);
            });
        }

        System.out.println("✅ Fixed categories loaded into DB");
    }
}
